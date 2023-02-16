package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.dao.model.SongArtistEntity;

@Mapper(componentModel = "spring")
public interface SongArtistMapper {

    // @Mapping(source = "documentName", target = "docname")
    // SampleModelEntity dtoToEntity(SampleModelCreateRequest sampleModel);

    SongArtistResponse entityToDto(SongArtistEntity songArtistEntity);

    // @Mapping(source = "documentName", target = "docname")
    // void updateEntity(SampleModelUpdateRequest sampleModel, @MappingTarget
    // SampleModelEntity sampleModelEntity);

    List<SongArtistResponse> entitiesToDtos(List<SongArtistEntity> sampleModelEntity);
}
