package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;

@Mapper(componentModel = "spring")
public interface ChordProgressionMapper {
    ChordProgressionEntity dtoToEntity(ChordProgressionCreateRequest sampleModel);

    ChordProgressionResponse entityToDto(ChordProgressionEntity sampleModelEntity);
}
