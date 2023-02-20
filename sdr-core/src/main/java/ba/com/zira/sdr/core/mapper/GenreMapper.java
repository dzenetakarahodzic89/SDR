package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.dao.model.GenreEntity;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreEntity dtoToEntity(GenreCreateRequest genre);

    Genre entityToDto(GenreEntity genreEntity);

    List<Genre> entitiesToDtos(List<GenreEntity> genreEntities);

    void updateEntity(GenreUpdateRequest genre, @MappingTarget GenreEntity genreEntity);

}
