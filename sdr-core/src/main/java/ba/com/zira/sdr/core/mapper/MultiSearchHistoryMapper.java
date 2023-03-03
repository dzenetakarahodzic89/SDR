package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryCreateRequest;
import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryResponse;
import ba.com.zira.sdr.dao.model.MultiSearchHistoryEntity;

@Mapper(componentModel = "spring")
public interface MultiSearchHistoryMapper {

    @Mapping(target = "dataStructure", ignore = true)
    MultiSearchHistoryEntity dtoToEntity(MultiSearchHistoryCreateRequest moritsIntegration);

    @Mapping(target = "dataStructure", ignore = true)
    MultiSearchHistoryResponse entityToDto(MultiSearchHistoryEntity moritsIntegrationEntity);

}
