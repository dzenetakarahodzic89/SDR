package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.dao.model.InstrumentEntity;

@Mapper(componentModel = "spring")
public interface InstrumentMapper {
    @Mapping(source = "instrumentName", target = "name")

    @Mapping(source = "instrumentInformation", target = "information")

    @Mapping(source = "instrumentType", target = "type")

    InstrumentEntity dtoToEntity(InstrumentCreateRequest instrumentCreateRequest);

    @Mapping(source = "instrumentName", target = "name")

    @Mapping(source = "instrumentInformation", target = "information")

    @Mapping(source = "instrumentType", target = "type")
    void updateEntity(InstrumentUpdateRequest instrumentUpdateRequest, @MappingTarget InstrumentEntity instrumentEntity);

    @Mapping(target = "instrumentName", source = "name")

    @Mapping(target = "instrumentInformation", source = "information")

    @Mapping(target = "instrumentStatus", source = "status")

    @Mapping(target = "instrumentType", source = "type")

    InstrumentResponse entityToDto(InstrumentEntity instrumentEntity);

    List<InstrumentResponse> entitiesToDtos(List<InstrumentEntity> instrumentEntity);

}
