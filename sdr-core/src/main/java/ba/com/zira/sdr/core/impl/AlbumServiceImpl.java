package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.AlbumService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumSearchRequest;
import ba.com.zira.sdr.api.model.album.AlbumSearchResponse;
import ba.com.zira.sdr.api.model.album.AlbumSongResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.core.mapper.AlbumMapper;
import ba.com.zira.sdr.core.mapper.SongArtistMapper;
import ba.com.zira.sdr.core.mapper.SongMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.utils.PlayTimeHelper;
import ba.com.zira.sdr.core.validation.AlbumRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
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
    SongArtistMapper songArtistMapper;
    AlbumMapper albumMapper;
    SongMapper songMapper;
    AlbumRequestValidation albumRequestValidation;
    LookupService lookupService;
    MediaService mediaService;

    @Override
    public PagedPayloadResponse<AlbumResponse> find(final FilterRequest request) {
        PagedData<AlbumEntity> albumEntities = albumDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, albumEntities, albumMapper::entitiesToDtos);
    }

    @Override
    public PagedPayloadResponse<AlbumSearchResponse> search(EntityRequest<AlbumSearchRequest> request) {
        var albumSearchRequest = request.getEntity();
        List<AlbumEntity> resultEntities = albumDAO.findAllAlbumsByNameGenreEraArtist(request.getEntity());
        List<AlbumSearchResponse> albumWithPlayTime = new ArrayList<>();
        for (var album : resultEntities) {
            var albumResponse = new AlbumSearchResponse();
            albumResponse.setId(album.getId());
            albumResponse.setName(album.getName());
            albumResponse.setOutlineText(album.getInformation());
            var albumSongs = songDAO.findAllByAlbumId(album.getId());
            int albumPlaytime = 0;
            for (var song : albumSongs) {
                if (song.getPlaytime() == null) {
                    continue;
                }
                var playTimeSplit = song.getPlaytime().split(":");
                var minutes = Integer.parseInt(playTimeSplit[0]);
                var seconds = Integer.parseInt(playTimeSplit[1]);
                albumPlaytime = minutes * 60 + seconds;
            }
            albumResponse.setPlaytime(albumPlaytime);
            albumWithPlayTime.add(albumResponse);

        }
        if (albumSearchRequest.getSort() != null && albumSearchRequest.getSort().equals("play_time")) {
            albumWithPlayTime.sort(new AlbumSortByPlayTime());
        }

        List<AlbumSearchResponse> pagedResponse = albumWithPlayTime;
        int firstIndex = (albumSearchRequest.getPageNumber() - 1) * albumSearchRequest.getPageSize();
        int lastIndex = firstIndex + albumSearchRequest.getPageSize();
        if (firstIndex >= 0 && lastIndex > firstIndex) {
            if (lastIndex < albumWithPlayTime.size()) {
                pagedResponse = albumWithPlayTime.subList(firstIndex, lastIndex);
            } else {
                pagedResponse = albumWithPlayTime.subList(firstIndex, albumWithPlayTime.size());
            }

        }

        for (var albumResponse : pagedResponse) {
            lookupService.lookupCoverImage(Arrays.asList(albumResponse), AlbumSearchResponse::getId, ObjectType.ALBUM.getValue(),
                    AlbumSearchResponse::setImageUrl, AlbumSearchResponse::getImageUrl);

        }
        var numberOfPages = albumWithPlayTime.size() / albumSearchRequest.getPageSize();
        if (albumWithPlayTime.size() % albumSearchRequest.getPageSize() != 0) {
            numberOfPages++;
        }
        var response = new PagedPayloadResponse<AlbumSearchResponse>();
        response.setResponseCode(ResponseCode.OK);
        response.setPayload(pagedResponse);

        response.setPage(albumSearchRequest.getPageNumber());
        response.setRecordsPerPage(albumSearchRequest.getPageSize());
        response.setNumberOfRecords(resultEntities.size());
        response.setNumberOfPages(numberOfPages);

        return response;
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
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AlbumResponse> getById(final EntityRequest<Long> request) {
        List<SongResponse> listSong = albumDAO.findSongsWithPlaytimeForAlbum(request.getEntity());
        var albumEntity = albumDAO.findByPK(request.getEntity());
        var albumResponse = albumMapper.entityToDto(albumEntity);
        var songArtist = albumEntity.getSongArtists();
        if (songArtist.size() != 0) {
            albumResponse.setArtistName(albumEntity.getSongArtists().get(0).getArtist().getName());
        }
        albumResponse.setSongs(listSong);
        lookupService.lookupCoverImage(Arrays.asList(albumResponse), AlbumResponse::getId, ObjectType.ALBUM.getValue(),
                AlbumResponse::setImageUrl, AlbumResponse::getImageUrl);
        return new PayloadResponse<>(request, ResponseCode.OK, albumResponse);
    }

}

class AlbumSortByPlayTime implements Comparator<AlbumSearchResponse> {

    @Override
    public int compare(AlbumSearchResponse a, AlbumSearchResponse b) {

        return a.getPlaytime() - b.getPlaytime();
    }

}
