package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;
import ba.com.zira.sdr.dao.model.LyricEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 *
 * @author zira
 *
 */
@Mapper(componentModel = "spring")
public interface LyricMapper {

    @Mapping(source = "songId", target = "song.id")
    LyricEntity dtoToEntity(LyricCreateRequest lyric);

    @Mapping(source = "song.id", target = "songId")
    Lyric entityToDto(LyricEntity lyricEntity);

    @Mapping(source = "songId", target = "song.id")
    void updateEntity(LyricUpdateRequest lyric, @MappingTarget LyricEntity lyricEntity);

    List<Lyric> entitiesToDtos(List<LyricEntity> lyricEntity);

}