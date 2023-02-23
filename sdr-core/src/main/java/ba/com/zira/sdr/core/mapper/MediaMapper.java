package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectResponse;
import ba.com.zira.sdr.api.model.media.MediaResponse;
import ba.com.zira.sdr.api.model.media.MediaUpdateRequest;
import ba.com.zira.sdr.dao.model.MediaEntity;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    @Mapping(source = "objectId", target = "objectId")
    @Mapping(source = "objectType", target = "objectType")
    @Mapping(source = "mediaStores", target = "mediaStores")
    MediaObjectResponse entityToDto(MediaEntity mediaEntity);

    MediaEntity dtoToEntity(MediaCreateRequest media);

    void updateEntity(MediaUpdateRequest media, @MappingTarget MediaEntity mediaEntity);

    void findEntity(MediaObjectRequest media, @MappingTarget MediaEntity mediaEntity);

    List<MediaResponse> entitiesToDtos(List<MediaEntity> mediaEntity);

    MediaObjectResponse entitiesToDto(MediaEntity mediaEntity);

    MediaResponse mediaEntityToResponses(MediaEntity mediaEntity);

}
