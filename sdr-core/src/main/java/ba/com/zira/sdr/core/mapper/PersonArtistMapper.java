package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.api.model.personartist.PersonArtistResponse;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;

@Mapper(componentModel = "spring")
public interface PersonArtistMapper {

    @Mapping(target = "artist.id", source = "artistId")
    @Mapping(target = "person.id", source = "personId")
    PersonArtistEntity dtoToEntity(PersonArtistCreateRequest personArtistCreateRequest);

    @Mapping(target = "artistId", source = "artist.id")
    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "artistName", source = "artist.name")
    @Mapping(target = "personName", source = "person.name")
    PersonArtistResponse entityToDto(PersonArtistEntity personArtistEntity);

    List<PersonArtistResponse> entitiesToDtos(List<PersonArtistEntity> personArtistEntity);

}
