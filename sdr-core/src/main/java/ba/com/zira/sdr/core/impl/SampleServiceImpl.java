package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.enums.Indicator;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.model.SampleModelCreateRequest;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;
import ba.com.zira.sdr.core.validation.SampleRequestValidation;
import ba.com.zira.sdr.mapper.SampleModelMapper;
import ba.com.zira.sdr.dao.SampleDAO;
import ba.com.zira.sdr.api.SampleService;
import ba.com.zira.sdr.api.model.SampleModel;
import ba.com.zira.sdr.dao.model.SampleModelEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SampleServiceImpl implements SampleService {

    private SampleDAO sampleDAO;
    private SampleModelMapper sampleModelMapper;
    private SampleRequestValidation sampleRequestValidation;

    @Override
    public PagedPayloadResponse<SampleModel> find(final FilterRequest request) throws ApiException {
            PagedData<SampleModelEntity> sampleModelEntities = sampleDAO.findAll(request.getFilter());
            return new PagedPayloadResponse<>(request, ResponseCode.OK, sampleModelEntities, sampleModelMapper::entitiesToDtos);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SampleModel> create(final EntityRequest<SampleModelCreateRequest> request) throws ApiException {
        SampleModelEntity sampleModelEntity = sampleModelMapper.dtoToEntity(request.getEntity());
        sampleModelEntity.setStatus(Status.ACTIVE.value());
        sampleModelEntity.setCreated(LocalDateTime.now());
        sampleModelEntity.setCreatedBy(request.getUserId());
        
        sampleDAO.persist(sampleModelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, sampleModelMapper.entityToDto(sampleModelEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SampleModel> update(final EntityRequest<SampleModelUpdateRequest> request) throws ApiException {
        sampleRequestValidation.validateUpdateSampleModelRequest(request);

        SampleModelEntity sampleModelEntity = sampleDAO.findByPK(request.getEntity().getId());
        sampleModelMapper.updateEntity(request.getEntity(), sampleModelEntity);
        
        sampleModelEntity.setModified(LocalDateTime.now());
        sampleModelEntity.setModifiedBy(request.getUserId());
        sampleDAO.merge(sampleModelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, sampleModelMapper.entityToDto(sampleModelEntity));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SampleModel> activate(final EntityRequest<Long> request) throws ApiException {
        sampleRequestValidation.validateExistsSampleModelRequest(request);
        
        SampleModelEntity sampleModelEntity = sampleDAO.findByPK(request.getEntity());
        sampleModelEntity.setStatus(Status.ACTIVE.value());
        sampleModelEntity.setModified(LocalDateTime.now());
        sampleModelEntity.setModifiedBy(request.getUser().getUserId());
        sampleDAO.merge(sampleModelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, sampleModelMapper.entityToDto(sampleModelEntity));
    }
}
