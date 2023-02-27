package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.song.fft.SongFftResult;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;
import ba.com.zira.sdr.dao.model.SongFttResultEntity;

@Mapper(componentModel = "spring")
public interface SongFftMapper {

    @Mapping(source = "songID", target = "song.id")
    SongFttResultEntity dtoToEntity(SongFftResultCreateRequest sampleModel);

    @Mapping(source = "song.id", target = "songID")
    SongFftResult entityToDto(SongFttResultEntity sampleModelEntity);

    @Mapping(source = "songID", target = "song.id")
    void updateEntity(SongFftResultUpdateRequest sampleModel, @MappingTarget SongFttResultEntity sampleModelEntity);

    List<SongFftResult> entitiesToDtos(List<SongFttResultEntity> sampleModelEntity);
}
