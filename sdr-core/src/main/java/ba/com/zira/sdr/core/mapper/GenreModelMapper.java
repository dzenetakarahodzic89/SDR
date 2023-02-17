package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.genre.GenreModel;
import ba.com.zira.sdr.api.model.genre.GenreModelCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreModelUpdateRequest;
import ba.com.zira.sdr.api.model.genre.SubGenreModel;
import ba.com.zira.sdr.dao.model.GenreEntity;

@Mapper(componentModel = "spring")
public abstract class GenreModelMapper {

    public abstract GenreEntity dtoToEntity(GenreModelCreateRequest genreModel);

    public abstract GenreModel entityToDto(GenreEntity genreEntity);

    public abstract List<GenreModel> entitiesToDtos(List<GenreEntity> genreEntities);

    public abstract GenreEntity updateEntity(GenreModelUpdateRequest genreModel, @MappingTarget GenreEntity genreEntity);

    public abstract SubGenreModel entityToSubGenreDto(GenreEntity genreEntity);

    public abstract List<SubGenreModel> entitiesToSubGenreDtos(List<GenreEntity> genreEntities);

}
