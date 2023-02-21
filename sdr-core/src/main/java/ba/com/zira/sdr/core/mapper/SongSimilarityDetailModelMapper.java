package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModel;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelUpdateRequest;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;

@Mapper(componentModel = "spring")
public interface SongSimilarityDetailModelMapper {

    @Mapping(source = "songSampleId", target = "song.id")
    SongSimilarityDetailEntity dtoToEntity(SongSimilarityDetailModelCreateRequest songSimilarityDetail);

    @Mapping(source = "song.id", target = "songSampleId")
    SongSimilarityDetailModel entityToDto(SongSimilarityDetailEntity songSimilarityDetailEntity);

    @Mapping(source = "songSampleId", target = "song.id")
    void updateEntity(SongSimilarityDetailModelUpdateRequest songSimilarityDetail,
            @MappingTarget SongSimilarityDetailEntity songSimilarityDetailEntitiy);

    List<SongSimilarityDetailModel> entitiesToDtos(List<SongSimilarityDetailEntity> songSimilarityDetailEntity);

}
