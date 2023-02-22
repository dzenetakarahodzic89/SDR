package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.SongFFt.SongFftResult;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResultUpdateRequest;
import ba.com.zira.sdr.dao.model.SongFttResultEntity;

@Mapper(componentModel = "spring")
public interface SongFftMapper {
    // @Mapping(source = "releaseData", target = "dateOfRelease")
    // @Mapping(source = "fftResults", target = "song")
    SongFttResultEntity dtoToEntity(SongFftResultCreateRequest sampleModel);

    // @Mapping(source = "song", target = "fftResults")
    SongFftResult entityToDto(SongFttResultEntity sampleModelEntity);

    // @Mapping(source = "fftResults", target = "song")
    void updateEntity(SongFftResultUpdateRequest sampleModel, @MappingTarget SongFttResultEntity sampleModelEntity);

    List<SongFftResult> entitiesToDtos(List<SongFttResultEntity> sampleModelEntity);
}
