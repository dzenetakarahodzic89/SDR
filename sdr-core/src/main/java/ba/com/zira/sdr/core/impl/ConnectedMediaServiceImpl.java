package ba.com.zira.sdr.core.impl;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ConnectedMediaService;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMedia;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaCreateRequest;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaUpdateRequest;
import ba.com.zira.sdr.core.mapper.ConnectedMediaMapper;
import ba.com.zira.sdr.core.validation.ConnectedMediaRequestValidation;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import ba.com.zira.sdr.dao.model.ConnectedMediaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConnectedMediaServiceImpl implements ConnectedMediaService {
    ConnectedMediaDAO connectedMediaDAO;
    ConnectedMediaMapper connectedMediaMapper;
    ConnectedMediaRequestValidation connectedMediaRequestValidation;

    @Override
    public PagedPayloadResponse<ConnectedMedia> find(FilterRequest request) {
        PagedData<ConnectedMediaEntity> connectedMediaEntities = connectedMediaDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, connectedMediaEntities, connectedMediaMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ConnectedMedia> create(EntityRequest<ConnectedMediaCreateRequest> request) {
        connectedMediaRequestValidation.validateConnectedMediaCreateRequest(request);

        var connectedMediaEntity = connectedMediaMapper.dtoToEntity(request.getEntity());

        connectedMediaEntity.setStatus(Status.ACTIVE.value());
        connectedMediaEntity.setCreated(LocalDateTime.now());
        connectedMediaEntity.setCreatedBy(request.getUserId());
        connectedMediaDAO.persist(connectedMediaEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, connectedMediaMapper.entityToDto(connectedMediaEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ConnectedMedia> update(EntityRequest<ConnectedMediaUpdateRequest> request) {
        connectedMediaRequestValidation.validateConnectedMediaUpdateRequest(request);

        var connectedMediaEntity = connectedMediaDAO.findByPK(request.getEntity().getId());
        connectedMediaMapper.updateEntity(request.getEntity(), connectedMediaEntity);

        connectedMediaEntity.setModified(LocalDateTime.now());
        connectedMediaEntity.setModifiedBy(request.getUserId());
        connectedMediaDAO.merge(connectedMediaEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, connectedMediaMapper.entityToDto(connectedMediaEntity));
    }
    
    
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        connectedMediaRequestValidation.validateConnectedMediaDeleteRequest(request);
        connectedMediaDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK,
                String.format("Connected Media with id %s is successfully deleted!", request.getEntity()));
    }

}
