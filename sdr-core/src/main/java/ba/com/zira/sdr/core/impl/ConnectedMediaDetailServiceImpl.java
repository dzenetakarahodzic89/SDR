package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ConnectedMediaDetailService;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetail;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailCreateRequest;
import ba.com.zira.sdr.configuration.ConnectedMediaDetailDAO;
import ba.com.zira.sdr.core.mapper.ConnectedMediaDetailMapper;
import ba.com.zira.sdr.core.validation.ConnectedMediaDetailRequestValidation;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConnectedMediaDetailServiceImpl implements ConnectedMediaDetailService {
    ConnectedMediaDetailDAO connectedMediaDetailDAO;
    ConnectedMediaDAO connectedMediaDAO;
    ConnectedMediaDetailMapper connectedMediaDetailMapper;
    ConnectedMediaDetailRequestValidation connectedMediaDetailRequestValidation;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ConnectedMediaDetail> create(EntityRequest<ConnectedMediaDetailCreateRequest> request) throws ApiException {
        connectedMediaDetailRequestValidation.validateConnectedMediaDetailCreateRequest(request);

        var connectedMediaDetailEntity = connectedMediaDetailMapper.dtoToEntity(request.getEntity());

        var connectedMediaEntity = connectedMediaDAO.findByPK(request.getEntity().getConnectedMediaId());
        connectedMediaDetailEntity.setConnectedMedia(connectedMediaEntity);

        connectedMediaDetailEntity.setStatus(Status.ACTIVE.value());
        connectedMediaDetailEntity.setConnectionDate(LocalDateTime.now());
        connectedMediaDetailEntity.setCreated(LocalDateTime.now());
        connectedMediaDetailEntity.setCreatedBy(request.getUserId());
        connectedMediaDetailDAO.persist(connectedMediaDetailEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, connectedMediaDetailMapper.entityToDto(connectedMediaDetailEntity));
    }

}
