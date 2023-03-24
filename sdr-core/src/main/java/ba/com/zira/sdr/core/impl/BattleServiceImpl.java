package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.core.mapper.BattleMapper;
import ba.com.zira.sdr.dao.BattleDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BattleServiceImpl implements BattleService {
    BattleDAO battleDAO;
    BattleMapper battleMapper;

    @Override
    public PayloadResponse<Battle> getById(EntityRequest<Long> request) throws ApiException {
        var battleEntity = battleDAO.findByPK(request.getEntity());
        var battle = battleMapper.entityToDto(battleEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, battle);
    }

}
