package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;
import ba.com.zira.sdr.dao.model.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonEntity dtoToEntity(PersonCreateRequest personCreateRequest);

    void updateEntity(PersonUpdateRequest personUpdateRequest, @MappingTarget PersonEntity personEntity);

    PersonResponse entityToDto(PersonEntity personEntity);

    List<PersonResponse> entitiesToDtos(List<PersonEntity> personEntity);

}
