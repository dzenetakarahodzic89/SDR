package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.dao.model.BattleEntity;

@Component
@Mapper(componentModel = "spring")

public interface BattleMapper {

    BattleGenerateResponse entityToDto(BattleEntity battleEntity);

    BattleEntity dtoToEntity(BattleGenerateRequest battleGenerateRequest);

}
