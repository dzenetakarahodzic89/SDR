package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.media.MediaStoreResponse;
import ba.com.zira.sdr.api.model.media.MediaUpdateRequest;
import ba.com.zira.sdr.dao.model.MediaStoreEntity;

@Mapper(componentModel = "spring")
public interface MediaStoreMapper {

    MediaStoreEntity dtoToEntity(MediaCreateRequest mediaStore);

    MediaStoreResponse entityToDto(MediaStoreEntity mediaStoreEntity);

    void updateEntity(MediaUpdateRequest mediaStore, @MappingTarget MediaStoreEntity mediaStoreEntity);

    List<MediaStoreResponse> entitiesToDtos(List<MediaStoreEntity> mediaStoreEntity);

}
