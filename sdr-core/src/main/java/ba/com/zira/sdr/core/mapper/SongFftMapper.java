package ba.com.zira.sdr.core.mapper;

import ba.com.zira.sdr.api.model.song.fft.SongFftResult;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;
import ba.com.zira.sdr.dao.model.SongFttResultEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

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
