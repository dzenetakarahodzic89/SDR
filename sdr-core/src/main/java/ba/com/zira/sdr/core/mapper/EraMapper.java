package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.era.EraCreateRequest;
import ba.com.zira.sdr.api.model.era.EraResponse;
import ba.com.zira.sdr.api.model.era.EraUpdateRequest;
import ba.com.zira.sdr.dao.model.EraEntity;

@Mapper(componentModel = "spring")
public interface EraMapper {

    EraEntity dtoToEntity(EraCreateRequest eraCreateRequest);

    EraResponse entityToDto(EraEntity eraEntity);

    void updateEntity(EraUpdateRequest eraUpdateRequest, @MappingTarget EraEntity eraEntity);

    List<EraResponse> entitiesToDtos(List<EraEntity> eraEntity);
}
