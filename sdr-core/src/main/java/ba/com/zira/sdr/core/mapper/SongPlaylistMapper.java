package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songplaylist.SongPlaylist;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistCreateRequest;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;
import ba.com.zira.sdr.dao.model.SongPlaylistEntity;

@Mapper(componentModel = "spring")
public interface SongPlaylistMapper {

    SongPlaylistEntity dtoToEntity(SongPlaylistCreateRequest songPlaylist);

    SongPlaylist entityToDto(SongPlaylistEntity songPlaylistEntity);

    void updateEntity(SongPlaylistUpdateRequest songPlaylist, @MappingTarget SongPlaylistEntity songPlaylistEntity);

    List<SongPlaylist> entitiesToDtos(List<SongPlaylistEntity> songPlaylistEntity);

}