package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.dao.model.PlaylistEntity;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    @Mapping(source = "name", target = "name")
    PlaylistEntity dtoToEntity(PlaylistCreateRequest playlist);

    @Mapping(source = "name", target = "name")
    Playlist entityToDto(PlaylistEntity playlistEntity);

    @Mapping(source = "name", target = "name")
    void updateEntity(PlaylistUpdateRequest playlist, @MappingTarget PlaylistEntity playlistEntity);

    List<Playlist> entitiesToDtos(List<PlaylistEntity> playlistEntity);

}
