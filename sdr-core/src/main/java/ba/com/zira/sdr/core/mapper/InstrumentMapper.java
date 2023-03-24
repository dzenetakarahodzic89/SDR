package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentSearchResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.dao.model.InstrumentEntity;

@Mapper(componentModel = "spring")
public interface InstrumentMapper {

    InstrumentEntity dtoToEntity(InstrumentCreateRequest instrumentCreateRequest);

    void updateEntity(InstrumentUpdateRequest instrumentUpdateRequest, @MappingTarget InstrumentEntity instrumentEntity);

    InstrumentResponse entityToDto(InstrumentEntity instrumentEntity);

    List<InstrumentResponse> entitiesToDtos(List<InstrumentEntity> instrumentEntity);

    List<InstrumentSearchResponse> searchEntitiesToDtos(List<InstrumentEntity> instrumentEntity);
}
