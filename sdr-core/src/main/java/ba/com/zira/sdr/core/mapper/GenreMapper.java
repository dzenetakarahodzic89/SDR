package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.api.model.genre.SubGenre;
import ba.com.zira.sdr.dao.model.GenreEntity;

@Mapper(componentModel = "spring")
public abstract class GenreMapper {

    public abstract GenreEntity dtoToEntity(GenreCreateRequest genre);

    public abstract Genre entityToDto(GenreEntity genreEntity);

    public abstract List<Genre> entitiesToDtos(List<GenreEntity> genreEntities);

    public abstract GenreEntity updateEntity(GenreUpdateRequest genre, @MappingTarget GenreEntity genreEntity);

    public abstract SubGenre entityToSubGenreDto(GenreEntity genreEntity);

    public abstract List<SubGenre> entitiesToSubGenreDtos(List<GenreEntity> genreEntities);

}
