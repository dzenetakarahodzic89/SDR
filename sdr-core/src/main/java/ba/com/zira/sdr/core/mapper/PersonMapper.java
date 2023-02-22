package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.dao.model.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "personName", target = "name")
    @Mapping(source = "personSurname", target = "surname")
    @Mapping(source = "personGender", target = "gender")
    @Mapping(source = "personDateOfBirth", target = "dateOfBirth")
    @Mapping(source = "personDateOfDeath", target = "dateOfDeath")
    @Mapping(source = "personInformation", target = "information")

    PersonEntity dtoToEntity(PersonCreateRequest personCreateRequest);

    @Mapping(source = "personName", target = "name")
    @Mapping(source = "personSurname", target = "surname")
    @Mapping(source = "personGender", target = "gender")
    @Mapping(source = "personDateOfBirth", target = "dateOfBirth")
    @Mapping(source = "personDateOfDeath", target = "dateOfDeath")
    @Mapping(source = "personInformation", target = "information")
    void updateEntity(PersonUpdateRequest personUpdateRequest, @MappingTarget PersonEntity personEntity);

    @Mapping(target = "personName", source = "name")
    @Mapping(target = "personSurname", source = "surname")
    @Mapping(target = "personGender", source = "gender")
    @Mapping(target = "personInformation", source = "information")
    @Mapping(target = "personDateOfBirth", source = "dateOfBirth")
    @Mapping(target = "personDateOfDeath", source = "dateOfDeath")
    @Mapping(target = "personStatus", source = "status")
    PersonResponse entityToDto(PersonEntity personEntity);

    List<PersonResponse> entitiesToDtos(List<PersonEntity> personEntity);

}
