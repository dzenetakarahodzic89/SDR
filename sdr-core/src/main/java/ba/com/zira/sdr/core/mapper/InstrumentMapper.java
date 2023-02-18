package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.dao.model.InstrumentEntity;

@Mapper(componentModel = "spring")
public interface InstrumentMapper {
    InstrumentEntity dtoToEntity(InstrumentCreateRequest instrumentCreateRequest);

    void updateEntity(InstrumentUpdateRequest instrumentUpdateRequest, @MappingTarget InstrumentEntity instrumentEntity);

    InstrumentResponse entityToDto(InstrumentEntity instrumentEintity);

    List<InstrumentResponse> entitiesToDtos(List<InstrumentEntity> instrumentEntity);

}
