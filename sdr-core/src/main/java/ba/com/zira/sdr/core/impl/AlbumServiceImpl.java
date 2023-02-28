package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.AlbumService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumSongResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
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

    @Override
    public PagedPayloadResponse<AlbumResponse> find(final FilterRequest request) {
        PagedData<AlbumEntity> albumEntities = albumDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, albumEntities, albumMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AlbumResponse> create(final EntityRequest<AlbumCreateRequest> request) {
        albumRequestValidation.validateCreateAlbumRequest(request);
        var albumEntity = albumMapper.dtoToEntity(request.getEntity());
        albumEntity.setCreated(LocalDateTime.now());
        albumEntity.setStatus(Status.ACTIVE.value());
        albumEntity.setCreatedBy(request.getUserId());
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
