package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

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
import ba.com.zira.sdr.api.SongPlaylistService;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylist;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistCreateRequest;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongPlaylistMapper;
import ba.com.zira.sdr.core.validation.SongPlaylistRequestValidation;
import ba.com.zira.sdr.dao.SongPlaylistDAO;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongPlaylistServiceImpl implements SongPlaylistService {
    private SongPlaylistDAO songPlaylistDAO;
    private SongPlaylistMapper songPlaylistMapper;
    private SongPlaylistRequestValidation songPlaylistRequestValidation;

    @Override
    public PagedPayloadResponse<SongPlaylist> find(FilterRequest request) throws ApiException {
        PagedData<SongPlaylistEntity> songPlaylistEntity = songPlaylistDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songPlaylistEntity, songPlaylistMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongPlaylist> create(EntityRequest<SongPlaylistCreateRequest> request) throws ApiException {
        songPlaylistRequestValidation.validateCreateSongPlaylistRequest(request);
        var songPlaylistEntity = songPlaylistMapper.dtoToEntity(request.getEntity());
        songPlaylistEntity.setStatus(Status.ACTIVE.value());
        songPlaylistEntity.setCreated(LocalDateTime.now());
        songPlaylistEntity.setCreatedBy(request.getUserId());

        songPlaylistDAO.persist(songPlaylistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songPlaylistMapper.entityToDto(songPlaylistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongPlaylist> update(EntityRequest<SongPlaylistUpdateRequest> request) throws ApiException {
        songPlaylistRequestValidation.validateUpdateSongPlaylistRequest(request);

        var songPlaylistEntity = songPlaylistDAO.findByPK(request.getEntity().getId());
        songPlaylistMapper.updateEntity(request.getEntity(), songPlaylistEntity);

        songPlaylistEntity.setModified(LocalDateTime.now());
        songPlaylistEntity.setModifiedBy(request.getUserId());
        songPlaylistDAO.merge(songPlaylistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songPlaylistMapper.entityToDto(songPlaylistEntity));
    }

    @Override
    public PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException {
        songPlaylistRequestValidation.validateExistsSongPlaylistRequest(request);

        var songPlaylistEntity = songPlaylistDAO.findByPK(request.getEntity());
        songPlaylistDAO.remove(songPlaylistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "SongPlaylist succesful deleted");
    }

}
