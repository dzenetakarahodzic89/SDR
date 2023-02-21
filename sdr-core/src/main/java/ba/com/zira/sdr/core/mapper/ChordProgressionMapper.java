package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;

@Mapper(componentModel = "spring")
public interface ChordProgressionMapper {
    ChordProgressionEntity dtoToEntity(ChordProgressionCreateRequest sampleModel);

    ChordProgressionResponse entityToDto(ChordProgressionEntity sampleModelEntity);

    void updateEntity(ChordProgressionUpdateRequest chordProgressionUpdateRequest,
            @MappingTarget ChordProgressionEntity chordProgressionEntity);

    List<ChordProgressionResponse> entitiesToDtos(List<ChordProgressionEntity> chordProgressionEntity);
}
