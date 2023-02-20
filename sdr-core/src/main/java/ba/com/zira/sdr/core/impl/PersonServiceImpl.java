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
import ba.com.zira.sdr.api.PersonService;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.core.mapper.PersonMapper;
import ba.com.zira.sdr.core.validation.PersonRequestValidation;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.model.PersonEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
	PersonDAO personDAO;
	PersonMapper personMapper;
	PersonRequestValidation personRequestValidation;

	@Override
	public PagedPayloadResponse<PersonResponse> find(final FilterRequest filterRequest) throws ApiException {
		PagedData<PersonEntity> personEntities = personDAO.findAll(filterRequest.getFilter());
		return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, personEntities, personMapper::entitiesToDtos);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PayloadResponse<PersonResponse> create(final EntityRequest<PersonCreateRequest> request)
			throws ApiException {
		var personEntity = personMapper.dtoToEntity(request.getEntity());
		personEntity.setCreated(LocalDateTime.now());
		personEntity.setStatus(Status.INACTIVE.value());
		personEntity.setCreatedBy(request.getUserId());
		personEntity.setModified(LocalDateTime.now());
		personEntity.setModifiedBy(request.getUserId());

		personDAO.persist(personEntity);
		return new PayloadResponse<>(request, ResponseCode.OK, personMapper.entityToDto(personEntity));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PayloadResponse<PersonResponse> update(final EntityRequest<PersonUpdateRequest> entityRequest)
			throws ApiException {
		personRequestValidation.validateUpdatePersonRequest(entityRequest);
		PersonEntity personEntity = personDAO.findByPK(entityRequest.getEntity().getId());
		personMapper.updateEntity(entityRequest.getEntity(), personEntity);

		personEntity.setModified(LocalDateTime.now());
		personEntity.setModifiedBy(entityRequest.getUserId());

		personDAO.merge(personEntity);
		return new PayloadResponse<>(entityRequest, ResponseCode.OK, personMapper.entityToDto(personEntity));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PayloadResponse<PersonResponse> activate(final EntityRequest<Long> request) throws ApiException {
		personRequestValidation.validateExistsPersonRequest(request);
		var personEntity = personDAO.findByPK(request.getEntity());
		personEntity.setStatus(Status.ACTIVE.value());
		personEntity.setModified(LocalDateTime.now());
		personEntity.setModifiedBy(request.getUser().getUserId());
		personDAO.persist(personEntity);
		return new PayloadResponse<>(request, ResponseCode.OK, personMapper.entityToDto(personEntity));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PayloadResponse<PersonResponse> delete(final EntityRequest<Long> entityRequest) {
		personRequestValidation.validateExistsPersonRequest(entityRequest);
		PersonEntity deletedEntity = personDAO.findByPK(entityRequest.getEntity());

		personDAO.removeByPK(entityRequest.getEntity());
		return new PayloadResponse<>(entityRequest, ResponseCode.OK, personMapper.entityToDto(deletedEntity));
	}

}
