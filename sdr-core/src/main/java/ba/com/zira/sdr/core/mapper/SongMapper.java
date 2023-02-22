package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.api.model.song.SongCreateRequest;
import ba.com.zira.sdr.api.model.song.SongUpdateRequest;
import ba.com.zira.sdr.dao.model.SongEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 *
 * @author Faris
 *
 */
@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(source = "chordProgressionId", target = "chordProgression.id")
    @Mapping(source = "genreId", target = "genre.id")
    @Mapping(source = "remixId", target = "remix.id")
    @Mapping(source = "coverId", target = "cover.id")
    SongEntity dtoToEntity(SongCreateRequest song);

    @Mapping(source = "chordProgression.id", target = "chordProgressionId")
    @Mapping(source = "genre.id", target = "genreId")
    Song entityToDto(SongEntity songEntity);

    @Mapping(source = "chordProgressionId", target = "chordProgression.id")
    @Mapping(source = "genreId", target = "genre.id")
    @Mapping(source = "remixId", target = "remix.id")
    @Mapping(source = "coverId", target = "cover.id")
    void updateEntity(SongUpdateRequest song, @MappingTarget SongEntity songEntity);

    List<Song> entitiesToDtos(List<SongEntity> songEntity);
    
    // Merge issue
    SongEntity dtoToEntity(AlbumCreateRequest albumCreateRequest);

    SongResponse entityToDto(SongEntity songEntity);

    List<SongResponse> entitiesToDtos(List<SongEntity> songEntities);

}
