package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModel;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelUpdateRequest;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;

@Mapper(componentModel = "spring")
public abstract class SongSimilarityDetailModelMapper {

    public abstract SongSimilarityDetailEntity dtoToEntity(SongSimilarityDetailModelCreateRequest songsimilaritydetailModel);

    public abstract SongSimilarityDetailModel entityToDto(SongSimilarityDetailEntity songsimilaritydetailEntity);

    public abstract List<SongSimilarityDetailModel> entitiesToDtos(List<SongSimilarityDetailEntity> songsimilaritydetailsEntities);

    public abstract SongSimilarityDetailEntity updateEntity(SongSimilarityDetailModelUpdateRequest genreModel,
            @MappingTarget SongSimilarityDetailEntity genreEntity);

}
