package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.CountryRelationService;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationSingleResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.mapper.CountryMapper;
import ba.com.zira.sdr.core.mapper.CountryRelationsMapper;
import ba.com.zira.sdr.core.validation.CountryRequestValidation;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.CountryRelationsDAO;
import ba.com.zira.sdr.dao.model.CountryEntity;
import ba.com.zira.sdr.dao.model.CountryRelationEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryServiceRelationImpl implements CountryRelationService {
    @NonNull
    CountryDAO countryDAO;
    @NonNull
    CountryMapper countryMapper;
    @NonNull
    CountryRelationsMapper countryRelationsMapper;
    @NonNull
    CountryRequestValidation countryRequestValidation;
    @NonNull
    CountryRelationsDAO countryRelationsDAO;
    private N2bObjectMapper n2bObjectMapper = new N2bObjectMapper();

    @Override
    public PayloadResponse<CountryResponse> createCountriesRelation(final EntityRequest<CountryRelationCreateRequest> request)
            throws ApiException {

        CountryRelationEntity countryRelationEntity = countryRelationsMapper.dtoToEntity(request.getEntity());
        CountryEntity countryEntity = countryDAO.findByPK(request.getEntity().getCountryId());
        if (countryEntity == null) {
            throw new EntityNotFoundException("Country not found with ID: " + request.getEntity().getCountryId());
        }
        // Check if there is already a row with the same country_id and
        // country_relations
        List<CountryRelationEntity> existingRelations = countryRelationsDAO.findByCountryAndRelation(request.getEntity().getCountryId(),
                countryRelationEntity.getCountryRelation());
        if (!existingRelations.isEmpty()) {
            String existingRelationData = existingRelations.get(0).getCountry().getName();
            throw new EntityExistsException("Data for: " + existingRelationData + " already exists with this relation: ");

        } else {
            countryRelationEntity.setCreated(LocalDateTime.now());
            countryRelationEntity.setCreatedBy(request.getUserId());
            countryRelationEntity.setModified(LocalDateTime.now());
            countryRelationEntity.setStatus(Status.ACTIVE.value());
            countryRelationEntity.setCountry(countryEntity);
            countryRelationsDAO.persist(countryRelationEntity);
            return new PayloadResponse<>(request, ResponseCode.OK, countryRelationsMapper.entityToDto(countryRelationEntity));
        }
    }

    @Override
    public ListPayloadResponse<LoV> getCountryrelationsForCountry(EntityRequest<Long> request) throws ApiException {
        var countryRelation = countryRelationsDAO.getRelationForCountry(request.getEntity());
        var loVsToReturn = new ArrayList<LoV>();
        try {
            var countries = n2bObjectMapper.readValue(countryRelation.getCountryRelation(), CountryRelationSingleResponse.class);
            for (var country : countries.getRelations()) {
                loVsToReturn.add(new LoV(country.getCountryId(), country.getCountryName() + " (" + country.getTypeOfLink() + ")"));
            }
            return new ListPayloadResponse<>(request, ResponseCode.OK, loVsToReturn);
        } catch (Exception ex) {
            throw ApiException.createFrom(request, ResponseCode.UNSUPPORTED_OPERATION, "JSON could not be parsed");
        }
    }

}
