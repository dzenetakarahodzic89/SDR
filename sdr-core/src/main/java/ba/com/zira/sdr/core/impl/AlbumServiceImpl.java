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
import ba.com.zira.sdr.dao.model.AlbumEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    AlbumDAO albumDAO;
    SongArtistDAO songArtistDAO;
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

        var eras = albumSearchRequest.getEras();
        var genres = albumSearchRequest.getGenres();
        var artists = albumSearchRequest.getArtists();
        List<AlbumEntity> albumEntities = albumDAO.findAll();
        List<AlbumEntity> resultEntities = new ArrayList<>();
        Map<Long, List<SongResponse>> albumSongsMap = new HashMap<>();

        for (int i = 0; i < albumEntities.size(); i++) {
            boolean eraFound = false;
            boolean genreFound = false;
            boolean artistFound = false;
            boolean nameFound = false;

            var albumEntity = albumEntities.get(i);

            eraFound = eras == null || eras.isEmpty() || albumEntity.getEra() != null && eras.contains(albumEntity.getEra().getId());

            nameFound = albumSearchRequest.getName() == null
                    || albumSearchRequest.getName() != null && albumEntity.getName().contains(albumSearchRequest.getName());
            if (genres == null || genres.isEmpty()) {
                genreFound = true;
            } else {
                for (var songArtist : albumEntity.getSongArtists()) {
                    var song = songArtist.getSong();
                    if (song.getGenre() != null && genres.contains(song.getGenre().getId())) {
                        genreFound = true;
                    }
                }
                ;
            }

            if (artists == null || artists.isEmpty()) {
                artistFound = true;
            } else {
                for (int j = 0; j < albumEntity.getSongArtists().size(); j++) {
                    var songArtist = albumEntity.getSongArtists().get(j);
                    if (artists.contains(songArtist.getArtist().getId())) {
                        artistFound = true;
                    }
                }
                ;
            }

            if (eraFound && genreFound && artistFound && nameFound) {
                resultEntities.add(albumEntity);
            }
        }
        ;

        if (albumSearchRequest.getSort() != null) {
            if (albumSearchRequest.getSort().equals("last_edit")) {
                resultEntities.sort(new AlbumSortByModified());

            } else if (albumSearchRequest.getSort().equals("alphabetical")) {
                resultEntities.sort(new AlbumSortByName());

            } else if (albumSearchRequest.getSort().equals("play_time")) {
                resultEntities.sort(new AlbumSortByPlayTime());
            }
        }

        List<AlbumEntity> pagedEntities = resultEntities;
        // if(albumSearchRequest.getPageNumber() != null &&
        // albumSearchRequest.getPageSize()!=null) {
        int firstIndex = (albumSearchRequest.getPageNumber() - 1) * albumSearchRequest.getPageSize();
        int lastIndex = firstIndex + albumSearchRequest.getPageSize();
        if (firstIndex >= 0 && lastIndex > firstIndex) {
            if (lastIndex < resultEntities.size()) {
                pagedEntities = resultEntities.subList(firstIndex, lastIndex);
            } else {
                pagedEntities = resultEntities.subList(firstIndex, resultEntities.size());
            }

        }
        // }

        List<AlbumSearchResponse> pagedResponse = new ArrayList<>();
        for (var album : pagedEntities) {
            var albumResponse = new AlbumSearchResponse();
            albumResponse.setId(album.getId());
            albumResponse.setName(album.getName());
            albumResponse.setOutlineText(album.getInformation());
            lookupService.lookupCoverImage(Arrays.asList(albumResponse), AlbumSearchResponse::getId, ObjectType.ALBUM.getValue(),
                    AlbumSearchResponse::setImageUrl, AlbumSearchResponse::getImageUrl);
            pagedResponse.add(albumResponse);

        }
        var numberOfPages = resultEntities.size() / albumSearchRequest.getPageSize();
        if (resultEntities.size() % albumSearchRequest.getPageSize() != 0) {
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

class AlbumSortByModified implements Comparator<AlbumEntity> {

    @Override
    public int compare(AlbumEntity a, AlbumEntity b) {
        if (a.getModified() == null) {
            return -1;
        }
        if (b.getModified() == null) {
            return 1;
        }
        return a.getModified().compareTo(b.getModified());
    }

}

class AlbumSortByName implements Comparator<AlbumEntity> {

    @Override
    public int compare(AlbumEntity a, AlbumEntity b) {
        return a.getName().compareTo(b.getName());
    }

}

class AlbumSortByPlayTime implements Comparator<AlbumEntity> {

    @Override
    public int compare(AlbumEntity a, AlbumEntity b) {
        int playTimeA = 0;
        int playTimeB = 0;

        for (var songArtist : a.getSongArtists()) {
            if (songArtist.getSong().getPlaytime() == null) {
                continue;
            }

            var playTimeSplit = songArtist.getSong().getPlaytime().split(":");
            var minutes = Integer.parseInt(playTimeSplit[0]);
            var seconds = Integer.parseInt(playTimeSplit[1]);
            playTimeA = minutes * 60 + seconds;
        }
        for (var songArtist : b.getSongArtists()) {
            if (songArtist.getSong().getPlaytime() == null) {
                continue;
            }

            var playTimeSplit = songArtist.getSong().getPlaytime().split(":");
            var minutes = Integer.parseInt(playTimeSplit[0]);
            var seconds = Integer.parseInt(playTimeSplit[1]);
            playTimeB = minutes * 60 + seconds;
        }

        return playTimeA - playTimeB;
    }

}
