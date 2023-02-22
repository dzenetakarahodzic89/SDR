package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegration;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;
import ba.com.zira.sdr.dao.model.MoritsIntegrationEntity;

@Mapper(componentModel = "spring")
public interface MoritsIntegrationMapper {

    MoritsIntegrationEntity dtoToEntity(MoritsIntegrationCreateRequest moritsIntegration);

    MoritsIntegration entityToDto(MoritsIntegrationEntity moritsIntegrationEntity);

    void updateEntity(MoritsIntegrationUpdateRequest moritsIntegration, @MappingTarget MoritsIntegrationEntity moritsIntegrationEntity);

    List<MoritsIntegration> entitiesToDtos(List<MoritsIntegrationEntity> moritsIntegrationEntity);

}
