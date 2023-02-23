package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;


@Mapper(componentModel = "spring")
public interface DeezerIntegrationMapper {

    @Mapping(source = "documentName", target = "docname")
    DeezerIntegrationEntity dtoToEntity(DeezerIntegrationCreateRequest deezerIntegration);

    @Mapping(source = "docname", target = "documentName")
    DeezerIntegration entityToDto(DeezerIntegrationEntity deezerIntegrationEntity);

    @Mapping(source = "documentName", target = "docname")
    void updateEntity(DeezerIntegrationUpdateRequest deezerIntegration, @MappingTarget DeezerIntegrationEntity deezerIntegrationEntity);

    List<DeezerIntegration> entitiesToDtos(List<DeezerIntegrationEntity> deezerIntegrationEntity);

}