package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.exception.ApiRuntimeException;
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
                    var updateCountryRelation = new CountryRelation(countryRelation.getForeignCountryId(),
                            countryRelation.getForeignCountryName(), countryRelation.getTypeOfLink());

                    var jsonUpdateForeignRelation = "";

                    CountryRelation[] countryRelationArray = objectMapper.readValue(relationByCountryId.getCountryRelation(),
                            CountryRelation[].class);
                    List<CountryRelation> countryRelationList = new ArrayList<>(Arrays.asList(countryRelationArray));

                    countryRelationList.forEach(cr -> {
                        if (cr.getTypeOfLink().equalsIgnoreCase(updateCountryRelation.getTypeOfLink())
                                && cr.getForeignCountryId().equals(updateCountryRelation.getForeignCountryId())) {
                            throw ApiRuntimeException.createFrom(ResponseCode.REQUEST_INVALID,
                                    String.format("Data for:%s already exists", updateCountryRelation.getForeignCountryName()));
                        }
                    });

                    countryRelationList.add(updateCountryRelation);
                    jsonUpdateForeignRelation = objectMapper.writeValueAsString(countryRelationList);

                    relationByCountryId.setModifiedBy(request.getUserId());
                    relationByCountryId.setModified(LocalDateTime.now());
                    relationByCountryId.setCountryRelation(jsonUpdateForeignRelation);
                    countryRelationsDAO.persist(relationByCountryId);
                } else {
                    var countryRelations = new ArrayList<CountryRelation>();
                    var rq = r.getCountryRelation();
                    var newCountryRelation = new CountryRelation(rq.getForeignCountryId(), rq.getForeignCountryName(), rq.getTypeOfLink());
                    countryRelations.add(newCountryRelation);

                    var jsonCreateCountryRelation = "";
                    jsonCreateCountryRelation = objectMapper.writeValueAsString(countryRelations);
                    countryRelationEntity.setCreated(LocalDateTime.now());
                    countryRelationEntity.setCreatedBy(request.getUserId());
                    countryRelationEntity.setStatus(Status.ACTIVE.value());
                    countryRelationEntity.setCountry(countryEntity);
                    countryRelationEntity.setCountryRelation(jsonCreateCountryRelation);
                    countryRelationsDAO.persist(countryRelationEntity);
                }
            } catch (Exception e) {
                throw ApiException.createFrom(request, e);
            }
        }
        return new PayloadResponse<>(request, ResponseCode.OK, "List of relations Saved!");
    }

}
