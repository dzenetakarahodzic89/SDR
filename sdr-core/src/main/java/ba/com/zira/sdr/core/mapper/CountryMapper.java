package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.dao.model.CountryEntity;

@Mapper(componentModel = "spring")

public interface CountryMapper {
    CountryEntity dtoToEntity(CountryCreateRequest countryCreateRequest);

    CountryResponse entityToDto(CountryEntity countryEntity);

    void updateEntity(CountryUpdateRequest updateRequest, @MappingTarget CountryEntity countryEntity);

    List<CountryResponse> entitiesToDtos(List<CountryEntity> sampleModelEntity);

}
