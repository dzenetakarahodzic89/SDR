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
import ba.com.zira.sdr.api.PersonArtistService;
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.api.model.personartist.PersonArtistResponse;
import ba.com.zira.sdr.core.mapper.PersonArtistMapper;
import ba.com.zira.sdr.core.validation.PersonArtistRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonArtistServiceImpl implements PersonArtistService {

    PersonArtistDAO personArtistDAO;

    ArtistDAO artistDAO;
    PersonDAO personDAO;
    PersonArtistMapper personArtistMapper;
    PersonArtistRequestValidation personArtistRequestValidation;

    @Override
    public PagedPayloadResponse<PersonArtistResponse> get(final FilterRequest filterRequest) throws ApiException {
        PagedData<PersonArtistEntity> personArtistEntities = personArtistDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, personArtistEntities, personArtistMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<PersonArtistResponse> create(final EntityRequest<PersonArtistCreateRequest> entityRequest) throws ApiException {
        personArtistRequestValidation.validateCreatePersonArtistRequest(entityRequest);

        PersonArtistEntity personArtistEntity = personArtistMapper.dtoToEntity(entityRequest.getEntity());

        personArtistEntity.setStatus(Status.ACTIVE.value());

        personArtistEntity.setCreated(LocalDateTime.now());
        personArtistEntity.setCreatedBy(entityRequest.getUserId());

        personArtistEntity.setModified(LocalDateTime.now());
        personArtistEntity.setModifiedBy(entityRequest.getUserId());

        personArtistEntity.setStartOfRelaltionship(LocalDateTime.now());
        personArtistEntity.setEndOfRelationship(LocalDateTime.now());

        personArtistEntity.setPerson(personDAO.findByPK(personArtistEntity.getPerson().getId()));
        personArtistEntity.setArtist(artistDAO.findByPK(personArtistEntity.getArtist().getId()));
        personArtistDAO.persist(personArtistEntity);

        return new PayloadResponse<>(entityRequest, ResponseCode.OK, personArtistMapper.entityToDto(personArtistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> entityRequest) {
        personArtistRequestValidation.validateDeletePersonArtistRequest(entityRequest);
        personArtistDAO.removeByPK(entityRequest.getEntity());

        return new PayloadResponse<>(entityRequest, ResponseCode.OK, "Deleted record successfully!");
    }

}
