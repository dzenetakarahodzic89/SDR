package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.model.playlistga.GAHistoryResponse;
import ba.com.zira.sdr.dao.model.GAHistoryEntity;

@Component
@Mapper(componentModel = "spring")
public interface GAHistoryMapper {
    GAHistoryResponse entityToDto(GAHistoryEntity gaHistoryEntity);

    List<GAHistoryResponse> entitiesToDtos(List<GAHistoryEntity> artistEntity);
}
