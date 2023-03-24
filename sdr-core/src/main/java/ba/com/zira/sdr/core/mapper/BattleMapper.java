package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.dao.model.BattleEntity;

@Component
@Mapper(componentModel = "spring")
public interface BattleMapper {

    @Mapping(source = "country.id", target = "winnerCountryId")
    @Mapping(source = "country.name", target = "winnerCountryName")
    Battle entityToDto(BattleEntity battleEntity);

    List<Battle> entitiesToDtos(List<BattleEntity> battleEntities);

}
