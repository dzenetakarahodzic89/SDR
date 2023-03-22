package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationResponse;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.model.ItunesIntegrationEntity;

@Mapper(componentModel = "spring")

public interface ItunesIntegrationMapper {
    ItunesIntegrationEntity dtoToEntity(ItunesIntegrationCreateRequest itunesIntegrationCreateRequest);
    ItunesIntegrationEntity dtoToEntity(ItunesIntegrationUpdateRequest itunesIntegrationUpdateRequest);

    ItunesIntegrationResponse entityToDto(ItunesIntegrationEntity itunesIntegrationEntity);

    void updateEntity(ItunesIntegrationUpdateRequest updateRequest, @MappingTarget ItunesIntegrationEntity itunesIntegrationEntity);

    List<ItunesIntegrationResponse> entitiesToDtos(List<ItunesIntegrationEntity> sampleModelEntity);

}
