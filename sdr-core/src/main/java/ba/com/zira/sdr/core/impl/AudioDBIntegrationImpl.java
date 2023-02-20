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
import ba.com.zira.sdr.api.AudioDBIntegrationService;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegration;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationUpdateRequest;
import ba.com.zira.sdr.core.mapper.AudioDBIntegrationMapper;
import ba.com.zira.sdr.core.validation.AudioDBIntegrationRequestValidation;
import ba.com.zira.sdr.dao.AudioDBIntegrationDAO;
import ba.com.zira.sdr.dao.model.AudioDBIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AudioDBIntegrationImpl implements AudioDBIntegrationService {

    AudioDBIntegrationDAO audioDBIntegrationDAO;
    AudioDBIntegrationMapper audioDBIntegrationMapper ;
    AudioDBIntegrationRequestValidation audioDBIntegrationRequestValidation;


    @Override
    public PagedPayloadResponse<AudioDBIntegration> find(final FilterRequest request) throws ApiException {
        PagedData<AudioDBIntegrationEntity> audioDBIntegrationEntities = audioDBIntegrationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, audioDBIntegrationEntities, audioDBIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AudioDBIntegration> create(final EntityRequest<AudioDBIntegrationCreateRequest> request) throws ApiException {
        var audioDBIntegrationEntity = audioDBIntegrationMapper.dtoToEntity(request.getEntity());

        audioDBIntegrationEntity.setStatus(Status.ACTIVE.value());
        audioDBIntegrationEntity.setCreated(LocalDateTime.now());
        audioDBIntegrationEntity.setCreatedBy(request.getUserId());

        audioDBIntegrationDAO.persist(audioDBIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, audioDBIntegrationMapper.entityToDto(audioDBIntegrationEntity));
        //return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, audioDBIntegrationEntity,  audioDBIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AudioDBIntegration> update(final EntityRequest<AudioDBIntegrationUpdateRequest> request) throws ApiException {
        audioDBIntegrationRequestValidation.validateUpdateAudioDBIntegrationRequest(request);

        var audioDBIntegrationEntity = audioDBIntegrationDAO.findByPK(request.getEntity().getId());
        audioDBIntegrationMapper.updateEntity(request.getEntity(), audioDBIntegrationEntity);

        audioDBIntegrationEntity.setModified(LocalDateTime.now());
        audioDBIntegrationEntity.setModifiedBy(request.getUserId());
        audioDBIntegrationDAO.merge(audioDBIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, audioDBIntegrationMapper.entityToDto(audioDBIntegrationEntity));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AudioDBIntegration> activate(final EntityRequest<Long> request) throws ApiException {
        audioDBIntegrationRequestValidation.validateExistsAudioDBIntegrationRequest(request);

        var audioDBIntegrationEntity = audioDBIntegrationDAO.findByPK(request.getEntity());
        audioDBIntegrationEntity.setStatus(Status.ACTIVE.value());
        audioDBIntegrationEntity.setModified(LocalDateTime.now());
        audioDBIntegrationEntity.setModifiedBy(request.getUser().getUserId());
        audioDBIntegrationDAO.merge(audioDBIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, audioDBIntegrationMapper.entityToDto(audioDBIntegrationEntity));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<AudioDBIntegration> delete(final EntityRequest<Long> request) throws ApiException {
        audioDBIntegrationRequestValidation.validateExistsAudioDBIntegrationRequest(request);

        var audioDBIntegrationEntity = audioDBIntegrationDAO.findByPK(request.getEntity());
        audioDBIntegrationDAO.remove(audioDBIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, audioDBIntegrationMapper.entityToDto(audioDBIntegrationEntity));
    }

}
