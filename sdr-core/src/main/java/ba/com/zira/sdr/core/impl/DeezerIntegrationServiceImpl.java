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
import ba.com.zira.sdr.api.DeezerIntegrationService;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.core.mapper.DeezerIntegrationMapper;
import ba.com.zira.sdr.core.validation.DeezerIntegrationRequestValidation;
import ba.com.zira.sdr.dao.DeezerIntegrationDAO;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeezerIntegrationServiceImpl implements DeezerIntegrationService {

    DeezerIntegrationDAO deezerIntegrationDAO;
    DeezerIntegrationMapper deezerIntegrationMapper;
    DeezerIntegrationRequestValidation DeezerIntegrationRequestValidation;

    @Override
    public PagedPayloadResponse<DeezerIntegration> find(final FilterRequest request) throws ApiException {
        PagedData<DeezerIntegrationEntity> deezerIntegrationEntities = deezerIntegrationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, deezerIntegrationEntities, deezerIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<DeezerIntegration> create(final EntityRequest<DeezerIntegrationCreateRequest> request) throws ApiException {
        var deezerIntegrationEntity = deezerIntegrationMapper.dtoToEntity(request.getEntity());

        deezerIntegrationEntity.setStatus(Status.ACTIVE.value());
        deezerIntegrationEntity.setCreated(LocalDateTime.now());
        deezerIntegrationEntity.setCreatedBy(request.getUserId());

        deezerIntegrationDAO.persist(deezerIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, deezerIntegrationMapper.entityToDto(deezerIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<DeezerIntegration> update(final EntityRequest<DeezerIntegrationUpdateRequest> request) {
        DeezerIntegrationRequestValidation.validateUpdateDeezerIntegrationRequest(request);

        var deezerIntegrationEntity = deezerIntegrationDAO.findByPK(request.getEntity().getId());
        deezerIntegrationMapper.updateEntity(request.getEntity(), deezerIntegrationEntity);

        deezerIntegrationEntity.setModified(LocalDateTime.now());
        deezerIntegrationEntity.setModifiedBy(request.getUserId());
        deezerIntegrationDAO.merge(deezerIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, deezerIntegrationMapper.entityToDto(deezerIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException {
        DeezerIntegrationRequestValidation.validateExistsDeezerIntegrationRequest(request);

        var deezerIntegrationEntity = deezerIntegrationDAO.findByPK(request.getEntity());
        deezerIntegrationDAO.remove(deezerIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "Deezer integration is removed.");
    }
}