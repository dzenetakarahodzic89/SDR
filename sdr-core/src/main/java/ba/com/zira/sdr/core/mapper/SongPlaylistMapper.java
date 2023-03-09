package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songplaylist.SongPlaylist;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistCreateRequest;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;

@Mapper(componentModel = "spring")
public interface SongPlaylistMapper {

    @Mapping(source = "playlistId", target = "playlist.id")
    @Mapping(source = "songId", target = "song.id")
    SongPlaylistEntity dtoToEntity(SongPlaylistCreateRequest songPlaylist);

    @Mapping(source = "song.id", target = "songId")
    @Mapping(source = "playlist.id", target = "playlistId")
    SongPlaylist entityToDto(SongPlaylistEntity songPlaylistEntity);

    @Mapping(source = "playlistId", target = "playlist.id")
    @Mapping(source = "songId", target = "song.id")
    void updateEntity(SongPlaylistUpdateRequest songPlaylist, @MappingTarget SongPlaylistEntity songPlaylistEntity);

    List<SongPlaylist> entitiesToDtos(List<SongPlaylistEntity> songPlaylistEntity);

}