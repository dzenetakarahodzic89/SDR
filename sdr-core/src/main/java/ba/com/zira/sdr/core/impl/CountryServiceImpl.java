package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.CountryService;
import ba.com.zira.sdr.api.model.country.CountriesSearchRequest;
import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryGetByIdsRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.mapper.CountryMapper;
import ba.com.zira.sdr.core.mapper.CountryRelationsMapper;
import ba.com.zira.sdr.core.validation.CountryRequestValidation;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.CountryRelationsDAO;
import ba.com.zira.sdr.dao.model.CountryEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    CountryDAO countryDAO;
    CountryMapper countryMapper;
    CountryRelationsMapper countryRelationsMapper;
    CountryRequestValidation countryRequestValidation;
    CountryRelationsDAO countryRelationsDAO;

    @Override
    public PagedPayloadResponse<CountryResponse> get(FilterRequest filterRequest) {
        PagedData<CountryEntity> countryList = countryDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, countryList, countryMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<CountryResponse> create(EntityRequest<CountryCreateRequest> request) {
        CountryEntity countryEntity = countryMapper.dtoToEntity(request.getEntity());

        countryEntity.setCreated(LocalDateTime.now());
        countryEntity.setCreatedBy(request.getUserId());
        countryEntity.setStatus(Status.ACTIVE.value());

        countryDAO.persist(countryEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, countryMapper.entityToDto(countryEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<CountryResponse> update(EntityRequest<CountryUpdateRequest> request) {
        countryRequestValidation.validateUpdateCountryRequest(request);

        CountryEntity countryEntity = countryDAO.findByPK(request.getEntity().getId());
        countryMapper.updateEntity(request.getEntity(), countryEntity);
        countryDAO.merge(countryEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, countryMapper.entityToDto(countryEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(EntityRequest<Long> request) {
        countryRequestValidation.validateDeleteCountryRequest(request);
        countryDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, "Country removed successfully!");
    }

    @Override
    public ListPayloadResponse<CountryResponse> getAll(EmptyRequest req) throws ApiException {
        List<CountryResponse> get = countryDAO.getAllCountries();
        return new ListPayloadResponse<>(req, ResponseCode.OK, get);
    }

    @Override
    public ListPayloadResponse<LoV> getAllCountriesExceptOneWithTheSelectedId(final EntityRequest<CountriesSearchRequest> request)
            throws ApiException {
        var countries = countryDAO.getAllCountriesExceptOneWithTheSelectedId(request.getEntity().getId());
        return new ListPayloadResponse<>(request, ResponseCode.OK, countries);
    }

    @Override
    public ListPayloadResponse<LoV> getAllCountryLoVsByIds(EntityRequest<CountryGetByIdsRequest> request) throws ApiException {
        var countries = countryDAO.getAllCountryLoVByIds(request.getEntity().getCountryIds());
        return new ListPayloadResponse<>(request, ResponseCode.OK, countries);
    }

}
