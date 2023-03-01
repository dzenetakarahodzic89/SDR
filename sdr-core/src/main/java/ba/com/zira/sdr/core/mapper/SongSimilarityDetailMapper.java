package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest2;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;

@Mapper(componentModel = "spring")
public interface SongSimilarityDetailMapper {

    @Mapping(source = "songSimilarity", target = "songSimilarity.id")
    SongSimilarityDetailEntity dtoToEntity1(SongSimilarityDetailCreateRequest2 songSimilarityDetail);

    @Mapping(source = "songSimilarity.id", target = "songSimilarityId")
    SongSimilarityDetail entityToDto(SongSimilarityDetailEntity songSimilarityDetailEntity);

    @Mapping(source = "songSimilarityId", target = "songSimilarity.id")
    void updateEntity(SongSimilarityDetailUpdateRequest songSimilarityDetail,
            @MappingTarget SongSimilarityDetailEntity songSimilarityDetailEntitiy);

    List<SongSimilarityDetail> entitiesToDtos(List<SongSimilarityDetailEntity> songSimilarityDetailEntity);

}
