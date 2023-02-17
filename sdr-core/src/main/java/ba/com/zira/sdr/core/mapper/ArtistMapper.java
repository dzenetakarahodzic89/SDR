package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.dao.model.ArtistEntity;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "dateOfDeath", source = "dateOfDeath")
    @Mapping(target = "information", source = "information")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "type", source = "type")
    ArtistResponse entityToDto(ArtistEntity entity);

    ArtistEntity dtoToEntity(ArtistCreateRequest request);

}
