package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
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
import ba.com.zira.sdr.api.LabelService;
import ba.com.zira.sdr.api.model.label.LabelCreateRequest;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;
import ba.com.zira.sdr.core.mapper.LabelMapper;
import ba.com.zira.sdr.core.validation.LabelRequestValidation;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.model.LabelEntity;

@Service

public class LabelServiceImpl implements LabelService {

    LabelDAO labelDAO;
    LabelMapper labelMapper;
    LabelRequestValidation labelReqVal;

    @Autowired
    public LabelServiceImpl(LabelDAO labelDAO, LabelMapper labelMapper, LabelRequestValidation labelReqVal) {
        super();
        this.labelDAO = labelDAO;
        this.labelMapper = labelMapper;
        this.labelReqVal = labelReqVal;
    }

    @Override
    public PagedPayloadResponse<LabelResponse> findAll(final FilterRequest request) throws ApiException {
        PagedData<LabelEntity> labelEntities = labelDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, labelEntities, labelMapper::entitiesToDtos);
    }

    @Override
    public PayloadResponse<LabelResponse> findById(final EntityRequest<Long> request) throws ApiException {
        labelReqVal.validateExistsLabelRequest(request);

        var labelEntity = labelDAO.findByPK(request.getEntity());

        return new PayloadResponse<>(request, ResponseCode.OK, labelMapper.entityToDto(labelEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<LabelResponse> create(final EntityRequest<LabelCreateRequest> request) throws ApiException {
        var labelEntity = labelMapper.dtoToEntity(request.getEntity());
        labelEntity.setStatus(Status.ACTIVE.value());
        labelEntity.setCreated(LocalDateTime.now());
        labelEntity.setCreatedBy(request.getUserId());
        labelDAO.persist(labelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, labelMapper.entityToDto(labelEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<LabelResponse> update(final EntityRequest<LabelUpdateRequest> request) throws ApiException {
        labelReqVal.validateUpdateLabelRequest(request);

        var labelEntity = labelDAO.findByPK(request.getEntity().getId());
        labelMapper.updateEntity(request.getEntity(), labelEntity);

        labelEntity.setModified(LocalDateTime.now());
        labelEntity.setModifiedBy(request.getUserId());
        labelDAO.merge(labelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, labelMapper.entityToDto(labelEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<LabelResponse> changeStatus(final EntityRequest<Long> request) throws ApiException {
        labelReqVal.validateExistsLabelRequest(request);

        var labelEntity = labelDAO.findByPK(request.getEntity());

        if ((labelEntity.getStatus()).toLowerCase().equals((Status.ACTIVE.value()).toLowerCase())) {
            labelEntity.setStatus(Status.INACTIVE.value());
        } else {
            labelEntity.setStatus(Status.ACTIVE.value());
        }

        labelEntity.setModified(LocalDateTime.now());
        labelEntity.setModifiedBy(request.getUser().getUserId());
        labelDAO.merge(labelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, labelMapper.entityToDto(labelEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException {
        labelReqVal.validateExistsLabelRequest(request);

        var labelEntity = labelDAO.findByPK(request.getEntity());
        if (labelEntity.getFounder() != null) {
            return new PayloadResponse<>(request, ResponseCode.ACCESS_DENIED, "Access denied because there is relation with founder");
        }
        labelDAO.remove(labelEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, "Label successfully deleted!");

    }

}
