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
import ba.com.zira.sdr.api.ShazamIntegrationService;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationResponse;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationUpdateRequest;
import ba.com.zira.sdr.core.mapper.ShazamIntegrationMapper;
import ba.com.zira.sdr.core.validation.ShazamIntegrationRequestValidation;
import ba.com.zira.sdr.dao.ShazamIntegrationDAO;
import ba.com.zira.sdr.dao.model.ShazaamIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShazamIntegrationServiceImpl implements ShazamIntegrationService {

	ShazamIntegrationDAO shazamIntegrationDAO;
	ShazamIntegrationMapper shazamIntegrationMapper;
	ShazamIntegrationRequestValidation shazamIntegrationRequestValidation;

	@Override
	public PagedPayloadResponse<ShazamIntegrationResponse> find(final FilterRequest request) throws ApiException {
		PagedData<ShazaamIntegrationEntity> shazamIntegrationEntities = shazamIntegrationDAO
				.findAll(request.getFilter());
		return new PagedPayloadResponse<>(request, ResponseCode.OK, shazamIntegrationEntities,
				shazamIntegrationMapper::entitiesToDtos);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PayloadResponse<ShazamIntegrationResponse> create(
			final EntityRequest<ShazamIntegrationCreateRequest> request) throws ApiException {
		var shazamIntegrationEntity = shazamIntegrationMapper.dtoToEntity(request.getEntity());
		shazamIntegrationEntity.setCreated(LocalDateTime.now());
		shazamIntegrationEntity.setCreatedBy(request.getUserId());
		shazamIntegrationEntity.setStatus(Status.ACTIVE.value());

		shazamIntegrationDAO.persist(shazamIntegrationEntity);
		return new PayloadResponse<>(request, ResponseCode.OK,
				shazamIntegrationMapper.entityToDto(shazamIntegrationEntity));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PayloadResponse<ShazamIntegrationResponse> update(
			final EntityRequest<ShazamIntegrationUpdateRequest> request) throws ApiException {
		shazamIntegrationRequestValidation.validateUpdateShazamIntegrationRequest(request);

		var shazamIntegrationEntity = shazamIntegrationDAO.findByPK(request.getEntity().getId());
		shazamIntegrationMapper.updateEntity(request.getEntity(), shazamIntegrationEntity);

		shazamIntegrationEntity.setModified(LocalDateTime.now());
		shazamIntegrationEntity.setModifiedBy(request.getUserId());
		shazamIntegrationDAO.merge(shazamIntegrationEntity);
		return new PayloadResponse<>(request, ResponseCode.OK,
				shazamIntegrationMapper.entityToDto(shazamIntegrationEntity));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException {
		shazamIntegrationRequestValidation.validateExistsShazamIntegrationRequest(request);

		var shazamIntegrationEntity = shazamIntegrationDAO.findByPK(request.getEntity());
		shazamIntegrationDAO.remove(shazamIntegrationEntity);
		return new PayloadResponse<>(request, ResponseCode.OK, "Integration removed successfully!");
	}
}
