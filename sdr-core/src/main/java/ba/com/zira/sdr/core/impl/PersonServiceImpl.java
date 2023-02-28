package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

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
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.PersonService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonCountryRequest;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.PersonMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.PersonEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    PersonDAO personDAO;
    CountryDAO countryDAO;
    PersonMapper personMapper;
    PersonRequestValidation personRequestValidation;
    LookupService lookupService;
    MediaService mediaService;

    @Override
    public PagedPayloadResponse<PersonResponse> find(final FilterRequest filterRequest) {
        PagedData<PersonEntity> personEntities = personDAO.findAll(filterRequest.getFilter());
        PagedData<PersonResponse> response = new PagedData<>();
        response.setRecords(personMapper.entitiesToDtos(personEntities.getRecords()));
        PagedDataMetadataMapper.remapMetadata(personEntities, response);
        lookupService.lookupCoverImage(response.getRecords(), PersonResponse::getId, ObjectType.PERSON.getValue(),
                PersonResponse::setImageUrl, PersonResponse::getImageUrl);
        lookupService.lookupCountryAbbriviation(response.getRecords(), PersonResponse::getCountryId, PersonResponse::setFlagAbbreviation);
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, response);
    }

    @Override
    public PayloadResponse<PersonResponse> findById(final EntityRequest<Long> request) {
        PersonEntity personEntities = personDAO.findByPK(request.getEntity());
        var response = personMapper.entityToDto(personEntities);
        lookupService.lookupCoverImage(Arrays.asList(response), PersonResponse::getId, ObjectType.PERSON.getValue(),
                PersonResponse::setImageUrl, PersonResponse::getImageUrl);
        lookupService.lookupCountryAbbriviation(Arrays.asList(response), PersonResponse::getCountryId, PersonResponse::setFlagAbbreviation);
        return new PayloadResponse<>(request, ResponseCode.OK, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<PersonResponse> create(final EntityRequest<PersonCreateRequest> request) throws ApiException {
        var personEntity = personMapper.dtoToEntity(request.getEntity());
        personEntity.setCreated(LocalDateTime.now());
        personEntity.setStatus(Status.ACTIVE.value());
        personEntity.setCreatedBy(request.getUserId());

        if (request.getEntity().getCoverImage() != null && request.getEntity().getCoverImageData() != null) {
            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(ObjectType.PERSON.getValue());
            mediaRequest.setObjectId(personEntity.getId());
            mediaRequest.setMediaObjectData(request.getEntity().getCoverImageData());
            mediaRequest.setMediaObjectName(request.getEntity().getCoverImage());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");
            mediaService.save(new EntityRequest<>(mediaRequest, request));
        }

        personDAO.persist(personEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, personMapper.entityToDto(personEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<PersonResponse> update(final EntityRequest<PersonUpdateRequest> entityRequest) {
        personRequestValidation.validateUpdatePersonRequest(entityRequest);
        var personEntity = personDAO.findByPK(entityRequest.getEntity().getId());
        personMapper.updateEntity(entityRequest.getEntity(), personEntity);

        personEntity.setModified(LocalDateTime.now());
        personEntity.setModifiedBy(entityRequest.getUserId());

        personDAO.merge(personEntity);
        return new PayloadResponse<>(entityRequest, ResponseCode.OK, personMapper.entityToDto(personEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        personRequestValidation.validateExistsPersonRequest(request);

        personDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK,
                String.format("Person with id %s is successfully deleted!", request.getEntity()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<PersonResponse> updatePersonCountry(final EntityRequest<PersonCountryRequest> request) {
        PersonEntity personEntity = personDAO.findByPK(request.getEntity().getPersonId());
        Long countryEntityId = request.getEntity().getCountryId();

        personEntity.setCountryId(countryEntityId);
        personEntity.setModified(LocalDateTime.now());
        personEntity.setModifiedBy(request.getUserId());
        personDAO.merge(personEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, personMapper.entityToDto(personEntity));
    }
}
