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
import ba.com.zira.sdr.api.CountryService;
import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.core.mapper.CountryMapper;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.model.CountryEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    CountryDAO countryDAO;
    CountryMapper countryMapper;

    @Override
    public PagedPayloadResponse<CountryResponse> get(FilterRequest filterRequest) throws ApiException {
        PagedData<CountryEntity> countryList = countryDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, countryList, countryMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<CountryResponse> create(EntityRequest<CountryCreateRequest> request) throws ApiException {
        CountryEntity countryEntity = countryMapper.dtoToEntity(request.getEntity());

        countryEntity.setCreated(LocalDateTime.now());
        countryEntity.setCreatedBy(request.getUserId());
        countryEntity.setStatus(Status.ACTIVE.value());

        countryDAO.persist(countryEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, countryMapper.entityToDto(countryEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<CountryResponse> update(EntityRequest<CountryUpdateRequest> request) throws ApiException {
        CountryEntity countryEntity = countryDAO.findByPK(request.getEntity().getId());
        countryMapper.updateEntity(request.getEntity(), countryEntity);
        countryDAO.merge(countryEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, countryMapper.entityToDto(countryEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException {
        countryDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, "Country removed successfully!");
    }
}
