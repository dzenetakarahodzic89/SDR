package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.ListRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.CountryRelationService;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelation;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;
import ba.com.zira.sdr.core.mapper.CountryMapper;
import ba.com.zira.sdr.core.mapper.CountryRelationsMapper;
import ba.com.zira.sdr.core.validation.CountryRequestValidation;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.CountryRelationsDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.CountryRelationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CountryServiceRelationImpl implements CountryRelationService {
    CountryDAO countryDAO;
    CountryMapper countryMapper;
    CountryRelationsMapper countryRelationsMapper;
    CountryRequestValidation countryRequestValidation;
    CountryRelationsDAO countryRelationsDAO;
    PersonDAO personDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> createCountriesRelation(final ListRequest<CountryRelationCreateRequest> request) throws ApiException {
        var objectMapper = new ObjectMapper();
        for (CountryRelationCreateRequest r : request.getList()) {
            try {
                var countryRelationEntity = countryRelationsMapper.dtoToEntity(r);
                var countryEntity = countryDAO.findByPK(r.getCountryId());
                var existingRelation = countryRelationsDAO.getCountryRelationByCountryId(r.getCountryId());

                if (countryEntity == null) {
                    throw ApiException.createFrom(request, ResponseCode.REQUEST_INVALID,
                            String.format("Country with ID:%s does not exist", r.getCountryId()));
                }
                if (!existingRelation.isEmpty()) {
                    // Update existing country relation

                    CountryRelationEntity relationByCountryId = countryRelationsDAO.relationByCountryId(r.getCountryId());

                    var countryRelation = r.getCountryRelation();

                    for (var i = 0; i < countryRelation.length; i++) {
                        var a = countryRelation[i];
                        var updateCountryRelation = new CountryRelation(a.getForeignCountryId(), a.getForeignCountryName(),
                                a.getTypeOfLink());

                        var jsonUpdateForeignRelation = "";

                        CountryRelation[] countryRelationArray = objectMapper.readValue(relationByCountryId.getCountryRelation(),
                                CountryRelation[].class);
                        List<CountryRelation> countryRelationList = new ArrayList<>(Arrays.asList(countryRelationArray));
                        var isEq = false;
                        for (var c = 0; c < countryRelationArray.length; c++) {
                            CountryRelation cr = countryRelationArray[c];

                            if (cr.getTypeOfLink().equalsIgnoreCase(updateCountryRelation.getTypeOfLink())
                                    && cr.getForeignCountryId().equals(updateCountryRelation.getForeignCountryId())) {
                                isEq = true;
                            }
                        }

                        if (isEq) {
                            continue;
                        }

                        countryRelationList.add(updateCountryRelation);
                        jsonUpdateForeignRelation = objectMapper.writeValueAsString(countryRelationList);

                        relationByCountryId.setModifiedBy(request.getUserId());
                        relationByCountryId.setModified(LocalDateTime.now());
                        relationByCountryId.setCountryRelation(jsonUpdateForeignRelation);
                        countryRelationsDAO.persist(relationByCountryId);
                        return new PayloadResponse<>(request, ResponseCode.OK, "The data has been updated successfully");

                    }
                } else {
                    var countryRelations = new ArrayList<CountryRelation>();
                    var rq = r.getCountryRelation();

                    for (var i = 0; i < rq.length; i++) {
                        var a = rq[i];
                        var newCountryRelation = new CountryRelation(a.getForeignCountryId(), a.getForeignCountryName(), a.getTypeOfLink());
                        countryRelations.add(newCountryRelation);
                    }

                    var jsonCreateCountryRelation = "";
                    jsonCreateCountryRelation = objectMapper.writeValueAsString(countryRelations);
                    countryRelationEntity.setCreated(LocalDateTime.now());
                    countryRelationEntity.setCreatedBy(request.getUserId());
                    countryRelationEntity.setStatus(Status.ACTIVE.value());
                    countryRelationEntity.setCountry(countryEntity);
                    countryRelationEntity.setCountryRelation(jsonCreateCountryRelation);
                    countryRelationsDAO.persist(countryRelationEntity);
                    return new PayloadResponse<>(request, ResponseCode.OK, "The Data has been created successfully");
                }

            } catch (Exception e) {
                throw ApiException.createFrom(request, e);
            }

        }
        return new PayloadResponse<>(request, ResponseCode.CONFIGURATION_ERROR, "The data already exists");

    }

}
