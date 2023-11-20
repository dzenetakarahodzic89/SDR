package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.songsimilarity.SongSimilarity;
import ba.com.zira.sdr.api.model.songsimilarity.SongSimilarityCreateRequest;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity;

@Mapper(componentModel = "spring")
public interface SongSimilarityMapper {

    @Mapping(source = "songA", target = "songA.id")
    @Mapping(source = "songB", target = "songB.id")
    SongSimilarityEntity dtoToEntity(SongSimilarityCreateRequest songSimilarity);

    @Mapping(source = "songA.id", target = "songA")
    @Mapping(source = "songB.id", target = "songB")
    SongSimilarity entityToDto(SongSimilarityEntity songSimilarityEntity);

}
