package ba.com.zira.sdr.core.impl;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SpotifyIntegrationService;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.zira.sdr.core.mapper.SpotifyIntegrationMapper;
import ba.com.zira.sdr.core.validation.SpotifyIntegrationRequestValidation;
import ba.com.zira.sdr.dao.SpotifyIntegrationDAO;
import ba.com.zira.sdr.dao.model.SpotifyIntegrationEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SpotifyIntegrationServiceImpl implements SpotifyIntegrationService {

    SpotifyIntegrationDAO spotifyIntegrationDAO;
    SpotifyIntegrationMapper spotifyIntegrationMapper;
    SpotifyIntegrationRequestValidation spotifyIntegrationRequestValidation;

    @Override
    public PagedPayloadResponse<SpotifyIntegrationResponse> find(final FilterRequest request) {
        PagedData<SpotifyIntegrationEntity> spotifyIntegrationEntities = spotifyIntegrationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationEntities, spotifyIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SpotifyIntegrationResponse> create(final EntityRequest<SpotifyIntegrationCreateRequest> request) {
        var spotifyIntegrationEntity = spotifyIntegrationMapper.dtoToEntity(request.getEntity());
        spotifyIntegrationEntity.setCreated(LocalDateTime.now());
        spotifyIntegrationEntity.setCreatedBy(request.getUserId());
        spotifyIntegrationEntity.setStatus(Status.ACTIVE.value());

        spotifyIntegrationDAO.persist(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationMapper.entityToDto(spotifyIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SpotifyIntegrationResponse> update(final EntityRequest<SpotifyIntegrationUpdateRequest> request) {
        spotifyIntegrationRequestValidation.validateUpdateSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationDAO.findByPK(request.getEntity().getId());
        spotifyIntegrationMapper.updateEntity(request.getEntity(), spotifyIntegrationEntity);

        spotifyIntegrationEntity.setModified(LocalDateTime.now());
        spotifyIntegrationEntity.setModifiedBy(request.getUserId());
        spotifyIntegrationDAO.merge(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationMapper.entityToDto(spotifyIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        spotifyIntegrationRequestValidation.validateExistsSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationDAO.findByPK(request.getEntity());
        spotifyIntegrationDAO.remove(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "Integration removed successfully!");
    }
}
