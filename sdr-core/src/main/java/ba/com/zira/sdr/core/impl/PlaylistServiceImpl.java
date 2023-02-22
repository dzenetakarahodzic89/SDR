package ba.com.zira.sdr.core.impl;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.PlaylistService;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.core.mapper.PlaylistMapper;
import ba.com.zira.sdr.core.validation.PlaylistRequestValidation;
import ba.com.zira.sdr.dao.PlaylistDAO;
import ba.com.zira.sdr.dao.model.PlaylistEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    PlaylistDAO playlistDAO;
    PlaylistMapper playlistMapper;
    PlaylistRequestValidation playlistRequestValidation;

    @Override
    public PagedPayloadResponse<Playlist> find(final FilterRequest request) {
        PagedData<PlaylistEntity> playlistEntities = playlistDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, playlistEntities, playlistMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Playlist> create(final EntityRequest<PlaylistCreateRequest> request) {
        var playlistEntity = playlistMapper.dtoToEntity(request.getEntity());
        playlistEntity.setStatus(Status.ACTIVE.value());
        playlistEntity.setCreated(LocalDateTime.now());
        playlistEntity.setCreatedBy(request.getUserId());

        playlistDAO.persist(playlistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, playlistMapper.entityToDto(playlistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Playlist> update(final EntityRequest<PlaylistUpdateRequest> request) {
        playlistRequestValidation.validateUpdatePlaylistRequest(request);

        var playlistEntity = playlistDAO.findByPK(request.getEntity().getId());
        playlistMapper.updateEntity(request.getEntity(), playlistEntity);

        playlistEntity.setModified(LocalDateTime.now());
        playlistEntity.setModifiedBy(request.getUserId());
        playlistDAO.merge(playlistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, playlistMapper.entityToDto(playlistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Playlist> delete(final EntityRequest<Long> request) {
        playlistRequestValidation.validateExistsPlaylistRequest(request);

        var playlistEntity = playlistDAO.findByPK(request.getEntity());
        playlistEntity.setStatus(Status.ACTIVE.value());
        playlistEntity.setModified(LocalDateTime.now());
        playlistEntity.setModifiedBy(request.getUser().getUserId());
        playlistDAO.merge(playlistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, playlistMapper.entityToDto(playlistEntity));
    }

}
