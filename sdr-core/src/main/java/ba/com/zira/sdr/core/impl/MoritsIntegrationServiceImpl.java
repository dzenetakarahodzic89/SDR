package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.MoritsIntegrationService;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegration;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;
import ba.com.zira.sdr.core.mapper.MoritsIntegrationMapper;
import ba.com.zira.sdr.core.validation.MoritsIntegrationRequestValidation;
import ba.com.zira.sdr.dao.MoritsIntegrationDAO;
import ba.com.zira.sdr.dao.model.MoritsIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MoritsIntegrationServiceImpl implements MoritsIntegrationService {

    MoritsIntegrationDAO moritsIntegrationDAO;
    MoritsIntegrationMapper moritsIntegrationMapper;
    MoritsIntegrationRequestValidation moritsIntegrationRequestValidation;

    @Override
    public PagedPayloadResponse<MoritsIntegration> find(final FilterRequest request) {
        PagedData<MoritsIntegrationEntity> moritsIntegrationEntities = moritsIntegrationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, moritsIntegrationEntities, moritsIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<MoritsIntegration> create(final EntityRequest<MoritsIntegrationCreateRequest> request) {
        var moritsIntegrationEntity = moritsIntegrationMapper.dtoToEntity(request.getEntity());
        moritsIntegrationEntity.setCreated(LocalDateTime.now());
        moritsIntegrationEntity.setCreatedBy(request.getUserId());
        moritsIntegrationEntity.setStatus(Status.ACTIVE.value());

        moritsIntegrationDAO.persist(moritsIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, moritsIntegrationMapper.entityToDto(moritsIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<MoritsIntegration> update(final EntityRequest<MoritsIntegrationUpdateRequest> request) {
        moritsIntegrationRequestValidation.validateUpdateMoritsIntegrationRequest(request);

        var moritsIntegrationEntity = moritsIntegrationDAO.findByPK(request.getEntity().getId());
        moritsIntegrationMapper.updateEntity(request.getEntity(), moritsIntegrationEntity);

        moritsIntegrationEntity.setModified(LocalDateTime.now());
        moritsIntegrationEntity.setModifiedBy(request.getUserId());
        moritsIntegrationDAO.merge(moritsIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, moritsIntegrationMapper.entityToDto(moritsIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        moritsIntegrationRequestValidation.validateExistsMoritsIntegrationRequest(request);

        var moritsIntegrationEntity = moritsIntegrationDAO.findByPK(request.getEntity());
        moritsIntegrationDAO.remove(moritsIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "Morits lyric integration removed successfully!");
    }

}
