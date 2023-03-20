package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.playlistga.PlaylistResponseGA;
import ba.com.zira.sdr.ga.impl.SongGene;

@Mapper(componentModel = "spring")
public interface SongScoreMapper {
    PlaylistResponseGA entityToDto(SongGene albumEntity);

    List<PlaylistResponseGA> entitiesToDtos(List<SongGene> albumEntities);
}
