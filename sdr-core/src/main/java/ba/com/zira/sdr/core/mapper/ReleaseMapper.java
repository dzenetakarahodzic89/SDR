package ba.com.zira.sdr.core.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ba.com.zira.sdr.api.model.release.ReleaseSearchResponse;
import ba.com.zira.sdr.dao.model.ReleaseEntity;

@Mapper(componentModel = "spring")
public interface ReleaseMapper {

    @Mapping(target = "lastUpdated", source = "releaseEntity", qualifiedByName = "lastUpdatedExtractor")
    ReleaseSearchResponse entityToDto(ReleaseEntity releaseEntity);

    @Named("lastUpdatedExtractor")
    default LocalDateTime timestampToLastUpdated(final ReleaseEntity releaseEntity) {
        return LocalDateTime.of(releaseEntity.getLastUpdated().toLocalDate(), releaseEntity.getLastUpdated().toLocalTime());
    }

    List<ReleaseSearchResponse> entitiesToDtos(List<ReleaseEntity> releaseEntities);
}
