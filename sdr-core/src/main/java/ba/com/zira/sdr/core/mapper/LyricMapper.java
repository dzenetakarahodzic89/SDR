package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;
import ba.com.zira.sdr.dao.model.LyricEntity;

@Mapper(componentModel = "spring")
public interface LyricMapper {

    @Mapping(source = "songId", target = "song.id")
    @Mapping(source = "songName", target = "song.name")
    LyricEntity dtoToEntity(LyricCreateRequest lyric);

    @Mapping(source = "song.id", target = "songId")
    @Mapping(source = "song.name", target = "songName")
    Lyric entityToDto(LyricEntity lyricEntity);

    @Mapping(source = "songId", target = "song.id")
    @Mapping(source = "songName", target = "song.name")
    void updateEntity(LyricUpdateRequest lyric, @MappingTarget LyricEntity lyricEntity);

    List<Lyric> entitiesToDtos(List<LyricEntity> lyricEntity);

}
