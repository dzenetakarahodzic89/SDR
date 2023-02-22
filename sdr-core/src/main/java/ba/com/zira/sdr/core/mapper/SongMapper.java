package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.dao.model.SongEntity;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongEntity dtoToEntity(AlbumCreateRequest albumCreateRequest);

    SongResponse entityToDto(SongEntity songEntity);

    List<SongResponse> entitiesToDtos(List<SongEntity> songEntities);
}
