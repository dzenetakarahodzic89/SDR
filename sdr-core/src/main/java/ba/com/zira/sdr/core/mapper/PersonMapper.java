package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.person.PersonArtistSingleResponse;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonOverviewResponse;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.dao.model.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "countryId", target = "country.id")
    PersonEntity dtoToEntity(PersonCreateRequest personCreateRequest);

    void updateEntity(PersonUpdateRequest personUpdateRequest, @MappingTarget PersonEntity personEntity);

    @Mapping(source = "country.id", target = "countryId")
    PersonResponse entityToDto(PersonEntity personEntity);

    List<PersonResponse> entitiesToDtos(List<PersonEntity> personEntity);

    List<PersonOverviewResponse> entitiessToDtos(List<PersonEntity> personEntity);

    PersonArtistSingleResponse entityToArtistSingleDtos(PersonEntity personEntity);

    @AfterMapping
    default void setFullName(@MappingTarget PersonResponse personResponse, PersonEntity person) {
        personResponse.setFullName(person.getName() + " " + person.getSurname());
    }

}
