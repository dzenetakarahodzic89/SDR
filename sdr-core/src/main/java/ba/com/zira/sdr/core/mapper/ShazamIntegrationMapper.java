package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationResponse;
import ba.com.zira.sdr.api.model.shazam.ShazamIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.model.ShazaamIntegrationEntity;

@Mapper(componentModel = "spring")
public interface ShazamIntegrationMapper {

	ShazaamIntegrationEntity dtoToEntity(ShazamIntegrationCreateRequest shazamIntegration);

	ShazamIntegrationResponse entityToDto(ShazaamIntegrationEntity shazamIntegrationEntity);

	void updateEntity(ShazamIntegrationUpdateRequest shazamIntegration,
			@MappingTarget ShazaamIntegrationEntity shazamIntegrationEntity);

	List<ShazamIntegrationResponse> entitiesToDtos(List<ShazaamIntegrationEntity> shazamIntegrationEntity);

}