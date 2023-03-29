package ba.com.zira.sdr.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleOverviewResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.MapState;
import ba.com.zira.sdr.api.model.battle.TeamsState;
import ba.com.zira.sdr.api.model.battle.TurnCombatState;
import ba.com.zira.sdr.core.mapper.BattleMapper;
import ba.com.zira.sdr.dao.BattleDAO;
import ba.com.zira.sdr.dao.BattleTurnDAO;
import ba.com.zira.sdr.dao.model.BattleEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    @NonNull
    BattleDAO battleDAO;
    BattleTurnDAO battleTurnDAO;

    @NonNull
    BattleMapper battleMapper;

    private N2bObjectMapper objectMapper = new N2bObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleServiceImpl.class);

    @Override
    public PagedPayloadResponse<BattleResponse> find(FilterRequest request) throws ApiException {
        PagedData<BattleEntity> battleEntities = battleDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, battleEntities, battleMapper::entityToDTOs);
    }

    @Override
    public PayloadResponse<Battle> getById(EntityRequest<Long> request) throws ApiException {
        var battleEntity = battleDAO.findByPK(request.getEntity());
        var battle = battleMapper.entityToBattleDto(battleEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, battle);
    }

    @Override
    public PayloadResponse<BattleSingleResponse> getLastTurn(EntityRequest<Long> request) throws ApiException {
        var battleTurn = battleDAO.findLastBattleTurn(request.getEntity());
        try {
            var mapState = objectMapper.readValue(battleTurn.getMapStateJson(), MapState.class);
            battleTurn.setMapState(mapState);
            var teamState = objectMapper.readValue(battleTurn.getTeamStateJson(), TeamsState.class);
            battleTurn.setTeamState(teamState);

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new PayloadResponse<>(request, ResponseCode.OK, battleTurn);
    }

    @Override
    public PayloadResponse<BattleSingleOverviewResponse> getSingleOverview(EntityRequest<Long> request) throws ApiException {
        var battleTurns = battleTurnDAO.getByBattleId(request.getEntity());
        var battleEntity = battleDAO.findByPK(request.getEntity());
        var winnerCountry = battleEntity.getCountry();
        int numberOfWins = 0;
        int totalNumberOfBattles = 0;
        var response = new BattleSingleOverviewResponse();

        try {
            for (var battleTurn : battleTurns) {
                var turnCombatState = objectMapper.readValue(battleTurn.getTurnCombatState(), TurnCombatState.class);
                for (var battleLog : turnCombatState.getBattleLogs()) {
                    if (battleLog.getWinnerCountryId() == winnerCountry.getId()) {
                        numberOfWins++;
                    }
                    totalNumberOfBattles++;
                }
            }
        } catch (Exception ex) {

            LOGGER.error(ex.getMessage());
        }
        float winPercentage = 100.0F * numberOfWins / totalNumberOfBattles;
        return new PayloadResponse<>(request, ResponseCode.OK, response);
    }

}
