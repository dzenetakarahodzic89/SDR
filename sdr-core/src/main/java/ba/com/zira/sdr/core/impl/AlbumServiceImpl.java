package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.AlbumService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.album.AlbumArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumSearchRequest;
import ba.com.zira.sdr.api.model.album.AlbumSearchResponse;
import ba.com.zira.sdr.api.model.album.AlbumSongResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.api.model.album.AlbumsByDecadeResponse;
import ba.com.zira.sdr.api.model.album.SongAudio;
import ba.com.zira.sdr.api.model.album.SongOfAlbumUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.core.mapper.AlbumMapper;
import ba.com.zira.sdr.core.mapper.SongArtistMapper;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.utils.PlayTimeHelper;
import ba.com.zira.sdr.core.validation.AlbumRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    AlbumDAO albumDAO;
    SongArtistDAO songArtistDAO;
    SongDAO songDAO;
    LabelDAO labelDAO;
    SongArtistMapper songArtistMapper;
    AlbumMapper albumMapper;
    SongMapper songMapper;
    AlbumRequestValidation albumRequestValidation;
    LookupService lookupService;
    MediaService mediaService;
    SongArtistService songArtistService;

    @Override
    public PagedPayloadResponse<AlbumResponse> find(final FilterRequest request) {
        PagedData<AlbumEntity> albumEntities = albumDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, albumEntities, albumMapper::entitiesToDtos);
    }

    @Override
    public PagedPayloadResponse<AlbumSearchResponse> search(SearchRequest<AlbumSearchRequest> request) {

        PagedData<AlbumSearchResponse> resultEntities = albumDAO.findAllAlbumsByNameGenreEraArtist(request);

        lookupService.lookupCoverImage(resultEntities.getRecords(), AlbumSearchResponse::getId, ObjectType.ALBUM.getValue(),
                AlbumSearchResponse::setImageUrl, AlbumSearchResponse::getImageUrl);
        return new PagedPayloadResponse<>(request, ResponseCode.OK, resultEntities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AlbumResponse> create(final EntityRequest<AlbumCreateRequest> request) throws ApiException {
        albumRequestValidation.validateCreateAlbumRequest(request);
        var albumEntity = albumMapper.dtoToEntity(request.getEntity());
        albumEntity.setCreated(LocalDateTime.now());
        albumEntity.setStatus(Status.ACTIVE.value());
        albumEntity.setCreatedBy(request.getUserId());

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {
            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.ALBUM.getValue());
            mediaRequest.setObjectId(albumEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));
        }
        albumDAO.persist(albumEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, albumMapper.entityToDto(albumEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AlbumResponse> update(final EntityRequest<AlbumUpdateRequest> request) {
        albumRequestValidation.validateUpdateAlbumRequest(request);
        var albumEntity = albumDAO.findByPK(request.getEntity().getId());
        albumMapper.updateEntity(request.getEntity(), albumEntity);
        albumEntity.setModifiedBy(request.getUserId());
        albumEntity.setModified(LocalDateTime.now());

        albumDAO.merge(albumEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, albumMapper.entityToDto(albumEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        albumRequestValidation.validateDeleteAlbumRequest(request);
        albumDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, "Album deleted successfully!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AlbumSongResponse> findAllSongsForAlbum(final EntityRequest<Long> request) {
        List<SongResponse> listSong = albumDAO.findSongsWithPlaytimeForAlbum(request.getEntity());
        List<String> playTimes = new ArrayList<>();
        Map<Long, SongResponse> map = new HashMap<>();

        listSong.forEach((final SongResponse s) -> {
            playTimes.add(s.getPlaytime());
            map.put(s.getId(), s);
        });

        String totalPlayTime = PlayTimeHelper.totalPlayTime(playTimes);
        var asp = new AlbumSongResponse();
        asp.setSongs(listSong);
        asp.setTotalPlayTime(totalPlayTime);
        asp.setMap(map);
        return new PayloadResponse<>(request, ResponseCode.OK, asp);
    }

    @Override
    public ListPayloadResponse<AlbumsByDecadeResponse> findAllAlbumsForArtist(EntityRequest<Long> request) throws ApiException {
        Long artistId = request.getEntity();
        List<AlbumArtistResponse> albumList = albumDAO.findAllAlbumsForArtist(artistId);

        Map<Integer, List<AlbumArtistResponse>> albumsByDecade = albumList.stream()
                .collect(Collectors.groupingBy(album -> album.getDateOfRelease().getYear() - (album.getDateOfRelease().getYear() % 10),
                        TreeMap::new, Collectors.toList()));

        List<AlbumsByDecadeResponse> albumsByDecadeList = new ArrayList<>();
        albumsByDecade.forEach((decade, albums) -> {
            albums.sort(Comparator.comparing(AlbumArtistResponse::getDateOfRelease));
            AlbumsByDecadeResponse decadeAlbums = new AlbumsByDecadeResponse(decade, albums);
            albumsByDecadeList.add(decadeAlbums);
        });

        return new ListPayloadResponse<>(request, ResponseCode.OK, albumsByDecadeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AlbumResponse> getById(final EntityRequest<Long> request) {
        List<SongResponse> listSong = albumDAO.findSongsWithPlaytimeForAlbum(request.getEntity());
        List<SongAudio> songAudioUrls = new ArrayList<>();
        lookupService.lookupAudio(listSong, SongResponse::getId, SongResponse::setAudioUrl);
        lookupService.lookupCoverImage(listSong, SongResponse::getId, ObjectType.SONG.getValue(), SongResponse::setCoverUrl,
                SongResponse::getCoverUrl);
        listSong.forEach(song -> {
            if (song.getAudioUrl() != null) {
                songAudioUrls.add(new SongAudio(song.getAudioUrl(), song.getName(), song.getCoverUrl()));
            }
        });
        var albumEntity = albumDAO.findByPK(request.getEntity());
        var albumResponse = albumMapper.entityToDto(albumEntity);
        albumResponse.setSongs(listSong);
        albumResponse.setAlbumArtists(albumDAO.findAllAlbumArtists(request.getEntity()));
        albumResponse.setAudioUrls(songAudioUrls);
        lookupService.lookupCoverImage(Arrays.asList(albumResponse), AlbumResponse::getId, ObjectType.ALBUM.getValue(),
                AlbumResponse::setImageUrl, AlbumResponse::getImageUrl);
        return new PayloadResponse<>(request, ResponseCode.OK, albumResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Song> addSongToAlbum(EntityRequest<SongOfAlbumUpdateRequest> request) throws ApiException {
        var existingEntriesForAlbum = songArtistDAO.songArtistByAlbum(request.getEntity().getAlbumId());

        songArtistDAO.deleteByAlbumId(request.getEntity().getAlbumId());

        existingEntriesForAlbum.forEach(songArtist -> {
            try {
                songArtistService.create(new EntityRequest<>(new SongArtistCreateRequest(songArtist.getSong().getId(),
                        songArtist.getArtist().getId(), songArtist.getLabel().getId(), request.getEntity().getAlbumId())));
            } catch (Exception e) {
                return;
            }
        });

        songArtistService.create(new EntityRequest<>(new SongArtistCreateRequest(request.getEntity().getSongId(),
                request.getEntity().getArtistId(), request.getEntity().getLabelId(), request.getEntity().getAlbumId())));

        var newSongEntity = songDAO.findByPK(request.getEntity().getSongId());

        return new PayloadResponse<>(request, ResponseCode.OK, songMapper.entityToDto(newSongEntity));

    }

    @Override
    public ListPayloadResponse<LoV> getAlbumLoVs(EmptyRequest request) throws ApiException {
        var albums = albumDAO.getAlbumLoVs();
        return new ListPayloadResponse<>(request, ResponseCode.OK, albums);
    }

}
