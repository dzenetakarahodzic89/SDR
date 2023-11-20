package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import ba.com.zira.sdr.dao.model.PlaylistEntity;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    PlaylistEntity dtoToEntity(PlaylistCreateRequest playlist);

    Playlist entityToDto(PlaylistEntity playlistEntity);

    void updateEntity(PlaylistUpdateRequest playlist, @MappingTarget PlaylistEntity playlistEntity);

    List<Playlist> entitiesToDtos(List<PlaylistEntity> playlistEntity);

}
