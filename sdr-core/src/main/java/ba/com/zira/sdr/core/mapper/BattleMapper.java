package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.dao.model.BattleEntity;

@Component
@Mapper(componentModel = "spring")
public interface BattleMapper {

    @Mapping(target = "countryName", source = "country.name")
    BattleResponse entityToDto(BattleEntity battleEntity);

    @Mapping(target = "countryName", source = "country.name")
    List<BattleResponse> entityToDTOs(List<BattleEntity> battleEntities);

    @Mapping(target = "winnerCountryName", source = "country.name")
    @Mapping(target = "winnerCountryId", source = "country.id")
    Battle entityToBattleDto(BattleEntity battleEntity);

    BattleGenerateResponse entityToDtoOne(BattleEntity battleEntity);

    BattleEntity dtoToEntity(BattleGenerateRequest battleGenerateRequest);

}
