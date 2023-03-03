package ba.com.zira.sdr.core.mapper;

import java.util.List;

import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;

@Mapper(componentModel = "spring")
public interface SongSimilarityDetailMapper {


    SongSimilarityDetailEntity dtoToEntity(SongSimilarityDetailCreateRequest songSimilarityDetail);


    SongSimilarityDetail entityToDto(SongSimilarityDetailEntity songSimilarityDetailEntity);


    void updateEntity(SongSimilarityDetailUpdateRequest songSimilarityDetail,
            @MappingTarget SongSimilarityDetailEntity songSimilarityDetailEntitiy);

    List<SongSimilarityDetail> entitiesToDtos(List<SongSimilarityDetailEntity> songSimilarityDetailEntity);

}
