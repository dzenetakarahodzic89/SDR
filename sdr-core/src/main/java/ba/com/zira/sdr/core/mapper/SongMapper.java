package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.album.SongOfAlbum;
import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.api.model.song.SongCreateRequest;
import ba.com.zira.sdr.api.model.song.SongResponse;
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

    @Mapping(source = "genreId", target = "genre.id")
    SongEntity dtoToEntity(SongCreateRequest song);

    @Mapping(source = "chordProgression.id", target = "chordProgressionId")
    @Mapping(source = "remix.id", target = "remixId")
    @Mapping(source = "cover.id", target = "coverId")
    @Mapping(source = "genre.id", target = "genreId")
    Song entityToDto(SongEntity songEntity);

    @Mapping(source = "genre.name", target = "genreName")
    SongOfAlbum entityToSongOfAlbumDto(SongEntity songEntity);

    void updateEntity(SongUpdateRequest song, @MappingTarget SongEntity songEntity);

    List<Song> entitiesToDtos(List<SongEntity> songEntity);

    List<SongResponse> entitiesToSongResponses(List<SongEntity> songEntities);

}
