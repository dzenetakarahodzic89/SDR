package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.dao.model.AlbumEntity;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(source = "eraId", target = "era.id")
    AlbumEntity dtoToEntity(AlbumCreateRequest albumCreateRequest);

    @Mapping(source = "era.id", target = "eraId")
    AlbumResponse entityToDto(AlbumEntity albumEntity);

    @Mapping(source = "eraId", target = "era.id")
    void updateEntity(AlbumUpdateRequest albumModel, @MappingTarget AlbumEntity albumEntity);

    List<AlbumResponse> entitiesToDtos(List<AlbumEntity> albumEntities);
}
