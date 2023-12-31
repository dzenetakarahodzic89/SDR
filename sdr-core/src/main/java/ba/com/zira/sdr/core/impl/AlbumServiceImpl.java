package ba.com.zira.sdr.core.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.request.ListRequest;
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
import ba.com.zira.sdr.api.model.album.AlbumArtistSongResponse;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumSearchRequest;
import ba.com.zira.sdr.api.model.album.AlbumSearchResponse;
import ba.com.zira.sdr.api.model.album.AlbumSongResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.api.model.album.AlbumsByDecadeResponse;
import ba.com.zira.sdr.api.model.album.AlbumsSongByDecade;
import ba.com.zira.sdr.api.model.album.SongAudio;
import ba.com.zira.sdr.api.model.album.SongOfAlbum;
import ba.com.zira.sdr.api.model.album.SongOfAlbumUpdateRequest;
import ba.com.zira.sdr.api.model.album.SongsAlbumResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
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
import ba.com.zira.sdr.dao.MediaDAO;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.MediaEntity;
import ba.com.zira.sdr.dao.model.MediaStoreEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    @NonNull
    AlbumDAO albumDAO;

    @NonNull
    SongArtistDAO songArtistDAO;

    @NonNull
    SongDAO songDAO;

    @NonNull
    LabelDAO labelDAO;

    @NonNull
    SongArtistMapper songArtistMapper;

    @NonNull
    AlbumMapper albumMapper;

    @NonNull
    SongMapper songMapper;

    @NonNull
    AlbumRequestValidation albumRequestValidation;

    @NonNull
    LookupService lookupService;

    @NonNull
    MediaService mediaService;

    @NonNull
    SongArtistService songArtistService;

    @NonNull
    MediaDAO mediaDAO;

    @NonNull
    MediaStoreDAO mediaStoreDAO;

    @Value("${image.default.url:http://172.20.20.45:82//vigor//img/mario.jpg}")
    String defaultImageUrl;

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
        albumDAO.persist(albumEntity);
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

        return new PayloadResponse<>(request, ResponseCode.OK, albumMapper.entityToDto(albumEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AlbumResponse> update(final EntityRequest<AlbumUpdateRequest> request) throws ApiException {
        albumRequestValidation.validateUpdateAlbumRequest(request);
        var albumEntity = albumDAO.findByPK(request.getEntity().getId());
        albumMapper.updateEntity(request.getEntity(), albumEntity);
        albumEntity.setModifiedBy(request.getUserId());
        albumEntity.setModified(LocalDateTime.now());

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
        for (AlbumsByDecadeResponse d : albumsByDecadeList) {
            d.setAlbumIds(d.getAlbums().stream().map(AlbumArtistResponse::getId).collect(Collectors.toList()));
        }

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
    public PayloadResponse<SongOfAlbum> addSongToAlbum(EntityRequest<SongOfAlbumUpdateRequest> request) throws ApiException {
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

        return new PayloadResponse<>(request, ResponseCode.OK, songMapper.entityToSongOfAlbumDto(newSongEntity));

    }

    @Override
    public ListPayloadResponse<LoV> getAlbumLoVs(EmptyRequest request) throws ApiException {
        var albums = albumDAO.getAlbumLoVs();
        return new ListPayloadResponse<>(request, ResponseCode.OK, albums);
    }

    @Override
    public ListPayloadResponse<AlbumsSongByDecade> findAllAlbumsSongForArtist(EntityRequest<Long> request) throws ApiException {
        Long artistId = request.getEntity();
        List<AlbumArtistSongResponse> albumList = albumDAO.findAllAlbumsSongForArtist(artistId);

        Map<Integer, List<AlbumArtistSongResponse>> albumsByDecade = albumList.stream()
                .collect(Collectors.groupingBy(album -> album.getDateOfRelease().getYear() - (album.getDateOfRelease().getYear() % 10),
                        TreeMap::new, Collectors.toList()));

        List<AlbumsSongByDecade> albumsByDecadeList = new ArrayList<>();
        albumsByDecade.forEach((decade, albums) -> {
            albums.sort(Comparator.comparing(AlbumArtistSongResponse::getDateOfRelease));
            var decadeAlbums = new AlbumsSongByDecade(decade, albums);
            albumsByDecadeList.add(decadeAlbums);
        });

        return new ListPayloadResponse<>(request, ResponseCode.OK, albumsByDecadeList);
    }

    @Override
    public ListPayloadResponse<SongsAlbumResponse> findAllSongsWithPlaytimeForAlbum(ListRequest<Long> request) throws ApiException {
        List<SongsAlbumResponse> listSong = albumDAO.findAllSongsWithPlaytimeForAlbum(request.getList());

        return new ListPayloadResponse<>(request, ResponseCode.OK, listSong);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> copyAlbumImageToSongs(EntityRequest<Long> request) throws ApiException {
        var now = LocalDateTime.now();
        var album = albumMapper.entityToDto(albumDAO.findByPK(request.getEntity()));
        lookupService.lookupCoverImage(Arrays.asList(album), AlbumResponse::getId, ObjectType.ALBUM.getValue(), AlbumResponse::setImageUrl,
                AlbumResponse::getImageUrl);
        if (album.getImageUrl().equals(defaultImageUrl)) {
            throw ApiException.createFrom(request, ResponseCode.REQUEST_INVALID,
                    "Album has no dedicated image so no copying will be done!");
        }
        MediaEntity media = mediaDAO.findByTypeAndId(ObjectType.ALBUM.getValue(), album.getId());
        List<MediaStoreEntity> mediaStores = mediaStoreDAO.getByMediaId(media.getId());
        List<SongEntity> songsOfAlbum = songDAO.findAllByAlbumId(request.getEntity());
        for (var song : songsOfAlbum) {
            var songMedia = mediaDAO.findByTypeAndId(ObjectType.SONG.getValue(), song.getId());
            if (songMedia == null) {
                songMedia = new MediaEntity();
                songMedia.setCreated(now);
                songMedia.setCreatedBy(request.getUserId());
                songMedia.setObjectId(song.getId());
                songMedia.setObjectType(ObjectType.SONG.getValue());
                mediaDAO.persist(songMedia);
            }
            for (var mediaStore : mediaStores) {
                var songMediaStore = new MediaStoreEntity();
                songMediaStore.setId(UUID.randomUUID().toString());
                songMediaStore.setCreated(now);
                songMediaStore.setCreatedBy(request.getUserId());
                songMediaStore.setUrl(mediaStore.getUrl());
                songMediaStore.setMedia(songMedia);
                songMediaStore.setType(mediaStore.getType());
                songMediaStore.setName(mediaStore.getName());
                songMediaStore.setExtension(mediaStore.getExtension());
                mediaStoreDAO.persist(songMediaStore);
            }
        }
        return new PayloadResponse<>(request, ResponseCode.OK, "Songs populated with album art!");

    }

}
