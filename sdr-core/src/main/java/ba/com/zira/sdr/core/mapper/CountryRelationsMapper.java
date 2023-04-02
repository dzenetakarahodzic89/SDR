package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationCreateRequest;
import ba.com.zira.sdr.dao.model.CountryRelationEntity;

@Mapper(componentModel = "spring")

public interface CountryRelationsMapper {
    @Mapping(source = "countryId", target = "country.id")
    @Mapping(target = "countryRelation", ignore = true)
    CountryRelationEntity dtoToEntity(CountryRelationCreateRequest countryRelationCreateRequest);

    CountryResponse entityToDto(CountryRelationEntity countryRelationEntity);
}
