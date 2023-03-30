package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.CountryRelationService;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelation;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationSingleResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.mapper.CountryMapper;
import ba.com.zira.sdr.core.mapper.CountryRelationsMapper;
import ba.com.zira.sdr.core.validation.CountryRequestValidation;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.CountryRelationsDAO;
import ba.com.zira.sdr.dao.PersonDAO;
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
    PersonDAO personDAO;
    private N2bObjectMapper n2bObjectMapper = new N2bObjectMapper();

    @Override
    public PayloadResponse<CountryResponse> createCountriesRelation(final EntityRequest<CountryRelationCreateRequest> request)
            throws ApiException {

        var objectMapper = new ObjectMapper();
        CountryRelationEntity countryRelationEntity = countryRelationsMapper.dtoToEntity(request.getEntity());
        CountryEntity countryEntity = countryDAO.findByPK(request.getEntity().getCountryId());
        var existingRelation = countryRelationsDAO.getCountryRelationByCountryId(request.getEntity().getCountryId());

        if (countryEntity == null) {
            throw new EntityNotFoundException("Country not found with ID: " + request.getEntity().getCountryId());
        }
        if (!existingRelation.isEmpty()) {
            // Update existing country realtion

            CountryRelationEntity relationByCountryId = countryRelationsDAO.relationByCountryId(request.getEntity().getCountryId());

            var r = request.getEntity().getCountryRelation();
            var updateCountryRelation = new CountryRelation(r.getForeignCountryId(), r.getForeignCountryName(), r.getTypeOfLink());

            String jsonUpdateForeignRelation = "";

            try {

                CountryRelation[] countryRelationArray = objectMapper.readValue(relationByCountryId.getCountryRelation(),
                        CountryRelation[].class);
                List<CountryRelation> countryRelationList = new ArrayList<>(Arrays.asList(countryRelationArray));

                countryRelationList.forEach(countryRelation -> {
                    if (countryRelation.getTypeOfLink().equalsIgnoreCase(updateCountryRelation.getTypeOfLink())
                            && countryRelation.getForeignCountryId().equals(updateCountryRelation.getForeignCountryId())) {
                        throw new RuntimeException("Data for:" + updateCountryRelation.getForeignCountryName() + " already exists");
                    }
                });

                countryRelationList.add(updateCountryRelation);
                jsonUpdateForeignRelation = objectMapper.writeValueAsString(countryRelationList);

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            relationByCountryId.setModifiedBy(request.getUserId());
            relationByCountryId.setModified(LocalDateTime.now());
            relationByCountryId.setCountryRelation(jsonUpdateForeignRelation);
            countryRelationsDAO.persist(relationByCountryId);
            return new PayloadResponse<>(request, ResponseCode.OK, countryRelationsMapper.entityToDto(relationByCountryId));
        }
        // Create new country realtion
        var countryRelations = new ArrayList<CountryRelation>();
        var rq = request.getEntity().getCountryRelation();
        var newCountryRelation = new CountryRelation(rq.getForeignCountryId(), rq.getForeignCountryName(), rq.getTypeOfLink());
        countryRelations.add(newCountryRelation);

        String jsonCreateCountryRelation = "";
        try {
            jsonCreateCountryRelation = objectMapper.writeValueAsString(countryRelations);

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        countryRelationEntity.setCreated(LocalDateTime.now());
        countryRelationEntity.setCreatedBy(request.getUserId());
        countryRelationEntity.setStatus(Status.ACTIVE.value());
        countryRelationEntity.setCountry(countryEntity);
        countryRelationEntity.setCountryRelation(jsonCreateCountryRelation);
        countryRelationsDAO.persist(countryRelationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, countryRelationsMapper.entityToDto(countryRelationEntity));
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
