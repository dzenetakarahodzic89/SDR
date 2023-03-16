package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

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
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.PersonService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.person.PersonCountryRequest;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonOverviewResponse;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonSearchRequest;
import ba.com.zira.sdr.api.model.person.PersonSearchResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.PersonMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.dao.model.CountryEntity;
import ba.com.zira.sdr.dao.model.PersonEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    PersonDAO personDAO;
    ArtistDAO artistDAO;
    AlbumDAO albumDAO;
    SongDAO songDAO;
    SongInstrumentDAO songInstrumentDAO;
    CountryDAO countryDAO;
    ConnectedMediaDAO connectedMediaDAO;
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
        var personCreateRequest = request.getEntity();
        var personEntity = personMapper.dtoToEntity(personCreateRequest);
        CountryEntity countryEntity = countryDAO.findByPK(request.getEntity().getCountryId());
        if (countryEntity == null) {
            throw new EntityNotFoundException("Country not found with ID: " + request.getEntity().getCountryId());
        }

        personEntity.setCreated(LocalDateTime.now());
        personEntity.setCreatedBy(request.getUserId());
        personEntity.setModified(LocalDateTime.now());
        personEntity.setModifiedBy(request.getUserId());
        personEntity.setStatus(Status.ACTIVE.value());

        personDAO.persist(personEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, personMapper.entityToDto(personEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<PersonResponse> update(final EntityRequest<PersonUpdateRequest> entityRequest) {
        personRequestValidation.validateUpdatePersonRequest(entityRequest);
        var personEntity = personDAO.findByPK(entityRequest.getEntity().getId());

        // Make sure that the country entity exists in the database
        CountryEntity countryEntity = countryDAO.findByPK(entityRequest.getEntity().getCountryId());
        if (countryEntity == null) {
            throw new EntityNotFoundException("Country not found with ID: " + entityRequest.getEntity().getCountryId());
        }

        // Update the person entity with the new values
        personEntity.setName(entityRequest.getEntity().getName());
        personEntity.setSurname(entityRequest.getEntity().getSurname());
        personEntity.setOutlineText(entityRequest.getEntity().getOutlineText());
        personEntity.setInformation(entityRequest.getEntity().getInformation());
        personEntity.setGender(entityRequest.getEntity().getGender());
        personEntity.setCountry(countryEntity);
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

        personEntity.setCountry(countryDAO.findByPK(countryEntityId));
        personEntity.setModified(LocalDateTime.now());
        personEntity.setModifiedBy(request.getUserId());
        personDAO.merge(personEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, personMapper.entityToDto(personEntity));
    }

    @Override
    public ListPayloadResponse<LoV> getPersonLoVs(final EmptyRequest req) throws ApiException {
        List<LoV> eras = personDAO.getAllPersonsLoV();
        return new ListPayloadResponse<>(req, ResponseCode.OK, eras);
    }

    @Override
    public PayloadResponse<PersonOverviewResponse> retrieveById(final EntityRequest<Long> request) {
        personRequestValidation.validateExistsPersonRequest(request);

        PersonOverviewResponse person = personDAO.getById(request.getEntity());
        person.setArtists(artistDAO.getArtistByPersonId(request.getEntity()));
        person.setAlbums(albumDAO.findAlbumByPersonId(request.getEntity()));
        person.setSongs(songDAO.findSongByPersonId(request.getEntity()));
        person.setConnectedMedia(connectedMediaDAO.getConnectedMediaByPersonId(request.getEntity()));
        person.setInstruments(songInstrumentDAO.getSongInstrumentByPersonId(request.getEntity()));

        lookupService.lookupCoverImage(Arrays.asList(person), PersonOverviewResponse::getId, ObjectType.PERSON.getValue(),
                PersonOverviewResponse::setImageUrl, PersonOverviewResponse::getImageUrl);
        lookupService.lookupCountryAbbriviation(Arrays.asList(person), PersonOverviewResponse::getCountryId,
                PersonOverviewResponse::setFlagAbbreviation);

        return new PayloadResponse<>(request, ResponseCode.OK, person);

    }

    @Override
    public ListPayloadResponse<PersonSearchResponse> search(final EntityRequest<PersonSearchRequest> req) throws ApiException {
        List<PersonSearchResponse> persons = personDAO.personSearch(req.getEntity().getName(), req.getEntity().getSortBy(),
                req.getEntity().getPersonGender(), req.getEntity().getPage(), req.getEntity().getPageSize());

        lookupService.lookupCoverImage(persons, PersonSearchResponse::getId, ObjectType.PERSON.getValue(),
                PersonSearchResponse::setImageUrl, PersonSearchResponse::getImageUrl);

        return new ListPayloadResponse<>(req, ResponseCode.OK, persons);
    }
}
