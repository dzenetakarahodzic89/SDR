package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequestExtend;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;

@Mapper(componentModel = "spring")
public interface DeezerIntegrationMapper {

    DeezerIntegrationEntity dtoToEntity(DeezerIntegrationCreateRequest deezerIntegration);

    DeezerIntegrationEntity dtoToEntityExtended(DeezerIntegrationCreateRequestExtend deezerIntegration);

    DeezerIntegration entityToDto(DeezerIntegrationEntity deezerIntegrationEntity);

    void updateEntity(DeezerIntegrationUpdateRequest deezerIntegration, @MappingTarget DeezerIntegrationEntity deezerIntegrationEntity);

    List<DeezerIntegration> entitiesToDtos(List<DeezerIntegrationEntity> deezerIntegrationEntity);

}