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
import ba.com.zira.sdr.api.InstrumentService;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.core.mapper.InstrumentMapper;
import ba.com.zira.sdr.core.validation.InstrumentRequestValidation;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.model.InstrumentEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class InstrumentServiceImpl implements InstrumentService {
    InstrumentDAO instrumentDAO;
    InstrumentMapper instrumentMapper;
    InstrumentRequestValidation instrumentRequestValidation;

    @Override
    public PagedPayloadResponse<InstrumentResponse> get(final FilterRequest filterRequest) throws ApiException {
        PagedData<InstrumentEntity> instrumentEntities = instrumentDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, instrumentEntities, instrumentMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<InstrumentResponse> create(final EntityRequest<InstrumentCreateRequest> request) throws ApiException {
        var instrumentEntity = instrumentMapper.dtoToEntity(request.getEntity());
        instrumentEntity.setStatus(Status.INACTIVE.value());
        instrumentEntity.setCreated(LocalDateTime.now());
        instrumentEntity.setModified(LocalDateTime.now());
        instrumentEntity.setCreatedBy(request.getUserId());
        instrumentEntity.setModifiedBy(request.getUserId());

        instrumentDAO.persist(instrumentEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, instrumentMapper.entityToDto(instrumentEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<InstrumentResponse> update(final EntityRequest<InstrumentUpdateRequest> entityRequest) throws ApiException {
        instrumentRequestValidation.validateUpdateInstrumentRequest(entityRequest);
        InstrumentEntity instrumentEntity = instrumentDAO.findByPK(entityRequest.getEntity().getId());
        instrumentMapper.updateEntity(entityRequest.getEntity(), instrumentEntity);

        instrumentEntity.setModified(LocalDateTime.now());
        instrumentEntity.setModifiedBy(entityRequest.getUserId());

        instrumentDAO.merge(instrumentEntity);
        return new PayloadResponse<>(entityRequest, ResponseCode.OK, instrumentMapper.entityToDto(instrumentEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<InstrumentResponse> activate(final EntityRequest<Long> request) throws ApiException {
        instrumentRequestValidation.validateExistsInstrumentRequest(request);

        var instrumentEntity = instrumentDAO.findByPK(request.getEntity());
        instrumentEntity.setStatus(Status.ACTIVE.value());
        instrumentEntity.setModified(LocalDateTime.now());
        instrumentEntity.setModifiedBy(request.getUser().getUserId());
        instrumentDAO.merge(instrumentEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, instrumentMapper.entityToDto(instrumentEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<InstrumentResponse> delete(final EntityRequest<Long> entityRequest) {
        instrumentRequestValidation.validateExistsInstrumentRequest(entityRequest);
        InstrumentEntity deletedEntity = instrumentDAO.findByPK(entityRequest.getEntity());

        instrumentDAO.removeByPK(entityRequest.getEntity());
        return new PayloadResponse<>(entityRequest, ResponseCode.OK, instrumentMapper.entityToDto(deletedEntity));
    }

}
