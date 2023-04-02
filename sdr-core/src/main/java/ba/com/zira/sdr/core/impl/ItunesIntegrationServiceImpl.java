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
import ba.com.zira.sdr.api.ItunesIntegrationService;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationResponse;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationUpdateRequest;
import ba.com.zira.sdr.core.mapper.ItunesIntegrationMapper;
import ba.com.zira.sdr.dao.ItunesIntegrationDAO;
import ba.com.zira.sdr.dao.model.ItunesIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItunesIntegrationServiceImpl implements ItunesIntegrationService{
   ItunesIntegrationDAO itunesintegrationDAO;
   ItunesIntegrationMapper itunesintegrationMapper;
   
    @Override
    public PagedPayloadResponse<ItunesIntegrationResponse> get(FilterRequest filterRequest) {
        PagedData<ItunesIntegrationEntity> itunesintegrationList = itunesintegrationDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, itunesintegrationList, itunesintegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ItunesIntegrationResponse> create(EntityRequest<ItunesIntegrationCreateRequest> request) {
        ItunesIntegrationEntity itunesintegrationEntity =itunesintegrationMapper.dtoToEntity(request.getEntity());

        itunesintegrationEntity.setCreated(LocalDateTime.now());
        itunesintegrationEntity.setCreatedBy(request.getUserId());
        itunesintegrationEntity.setStatus(Status.ACTIVE.value());

        itunesintegrationDAO.persist(itunesintegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK,itunesintegrationMapper.entityToDto(itunesintegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ItunesIntegrationResponse> update(EntityRequest<ItunesIntegrationUpdateRequest> request) {
   
        ItunesIntegrationEntity itunesintegrationEntity = itunesintegrationDAO.findByPK(request.getEntity().getId());
        itunesintegrationMapper.updateEntity(request.getEntity(), itunesintegrationEntity);
        itunesintegrationDAO.merge(itunesintegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, itunesintegrationMapper.entityToDto(itunesintegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(EntityRequest<Long> request) {
    	itunesintegrationDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, "Itunesintegration removed successfully!");
    }

}

