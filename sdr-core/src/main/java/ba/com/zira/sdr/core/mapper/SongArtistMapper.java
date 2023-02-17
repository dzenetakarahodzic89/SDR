package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.dao.model.SongArtistEntity;

@Mapper(componentModel = "spring")
public interface SongArtistMapper {

    // @Mapping(source = "documentName", target = "docname")
    // SampleModelEntity dtoToEntity(SampleModelCreateRequest sampleModel);

    @Mapping(target = "artistId", source = "artist.id")
    @Mapping(target = "songId", source = "song.id")
    @Mapping(target = "labelId", source = "label.id")
    @Mapping(target = "albumId", source = "album.id")
    @Mapping(target = "artistName", source = "artist.name")
    @Mapping(target = "songName", source = "song.name")
    @Mapping(target = "labelName", source = "label.name")
    @Mapping(target = "albumName", source = "album.name")
    SongArtistResponse entityToDto(SongArtistEntity songArtistEntity);

    // @Mapping(source = "documentName", target = "docname")
    // void updateEntity(SampleModelUpdateRequest sampleModel, @MappingTarget
    // SampleModelEntity sampleModelEntity);

    List<SongArtistResponse> entitiesToDtos(List<SongArtistEntity> sampleModelEntity);
}
