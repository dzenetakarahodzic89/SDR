package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

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
import ba.com.zira.sdr.api.model.battle.BattleLogEntry;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.MapState;
import ba.com.zira.sdr.api.model.battle.PreBattleCreateRequest;
import ba.com.zira.sdr.api.model.battle.TeamsState;
import ba.com.zira.sdr.api.model.battle.TurnCombatState;
import ba.com.zira.sdr.core.mapper.BattleMapper;
import ba.com.zira.sdr.dao.BattleDAO;
import ba.com.zira.sdr.dao.BattleTurnDAO;
import ba.com.zira.sdr.dao.model.BattleEntity;
import ba.com.zira.sdr.dao.model.BattleTurnEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    @NonNull
    BattleDAO battleDAO;
    @NonNull
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
    public PayloadResponse<String> preBattleTurn(EntityRequest<PreBattleCreateRequest> request) throws ApiException {
        var move = request.getEntity();
        var turnFull = battleTurnDAO.getFullBattleTurn(move.getBattleId());
        MapState mapState = null;
        TeamsState teamState = null;
        TurnCombatState combatState = null;
        try {
            mapState = objectMapper.readValue(turnFull.getMapState(), MapState.class);
            teamState = objectMapper.readValue(turnFull.getTeamState(), TeamsState.class);
            combatState = objectMapper.readValue(turnFull.getTurnCombatState(), TurnCombatState.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw ApiException.createFrom(request, ResponseCode.INVALID_SETUP, "Json could not be parsed");
        }
        var newRequest = new BattleTurnEntity();
        newRequest.setBattle(turnFull.getBattle());
        newRequest.setCreated(LocalDateTime.now());
        newRequest.setCreatedBy(turnFull.getCreatedBy());
        newRequest.setModified(turnFull.getModified());
        newRequest.setModifiedBy(turnFull.getModifiedBy());
        newRequest.setTurnNumber(turnFull.getTurnNumber() + 1);
        newRequest.setName(turnFull.getName().substring(0, turnFull.getName().indexOf('-')) + "- Turn " + (turnFull.getTurnNumber() + 1));
        if (Boolean.TRUE.equals(move.getIsAttackedPassive())) {
            for (var country : mapState.getCountries()) {
                if (Objects.equals(country.getCountryId(), move.getAttackedId())) {

                    country.setTeamOwnershipId(1L);
                    country.setMapValue((double) 1);
                    country.setCountryStatus("Active");
                    Long prevKey = combatState.getBattleLogs().get(0).getTextHistory().keySet().stream().max(Long::compare).orElse(0L);
                    combatState.getBattleLogs().get(0).getTextHistory().put(prevKey + 1,
                            "Country " + move.getAttackedName() + " is passive, " + move.getAttackerName() + " has taken over!");
                    teamState.getActivePlayerTeam().setNumberOfWins(teamState.getActivePlayerTeam().getNumberOfWins() + 1);
                    teamState.getActivePlayerTeam().setLastActiveTurn(teamState.getActivePlayerTeam().getLastActiveTurn() + 1);
                    teamState.getActivePlayerTeam().getEligibleCountryIds().add(move.getAttackedId());
                    mapState.setNumberOfActiveCountries(mapState.getNumberOfActiveCountries() + 1);
                    mapState.setNumberOfActivePlayerTeams(mapState.getNumberOfActivePlayerTeams() + 1);
                    mapState.setNumberOfActiveNpcTeams(mapState.getNumberOfActiveNpcTeams() - 1);
                    mapState.setNumberOfPassiveCountries(mapState.getNumberOfPassiveCountries() - 1);
                    try {
                        newRequest.setTeamState(objectMapper.writeValueAsString(teamState));
                        newRequest.setTurnCombatState(objectMapper.writeValueAsString(combatState));
                        newRequest.setMapState(objectMapper.writeValueAsString(mapState));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    newRequest.setStatus("Finished");
                    battleTurnDAO.persist(newRequest);
                    return new PayloadResponse<>(request, ResponseCode.OK, "You won against " + move.getAttackedName());
                }
            }
        } else {
            newRequest.setStatus("In process");
            Long prevKey = combatState.getBattleLogs().get(0).getTextHistory().keySet().stream().max(Long::compare).orElse(0L);
            combatState.getBattleLogs().get(0).getTextHistory().put(prevKey + 1,
                    "Country " + move.getAttackerName() + " is starting to attack " + move.getAttackedName());
            combatState.getBattleLogs().get(0).getTurnHistory().add(new BattleLogEntry());
            try {
                newRequest.setMapState(objectMapper.writeValueAsString(mapState));
                newRequest.setTeamState(objectMapper.writeValueAsString(teamState));
                newRequest.setTurnCombatState(objectMapper.writeValueAsString(combatState));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            battleTurnDAO.persist(newRequest);
            return new PayloadResponse<>(request, ResponseCode.OK, "Turn has been created.");
        }
        return null;
    }

}
