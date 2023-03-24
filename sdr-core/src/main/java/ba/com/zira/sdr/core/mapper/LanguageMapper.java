package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.LanguageEntity;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    LanguageEntity dtoToEntity(LoV albumCreateRequest);

    LoV entityToDto(LanguageEntity albumEntity);

    List<LoV> entitiesToDtos(List<LanguageEntity> albumEntities);
}
