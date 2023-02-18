package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.dao.model.AlbumEntity;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(source = "eraId", target = "era.id")
    AlbumEntity dtoToEntity(AlbumCreateRequest albumCreateRequest);

    @Mapping(source = "era.id", target = "eraId")
    AlbumCreateRequest entityToDto(AlbumEntity albumEntity);

}
