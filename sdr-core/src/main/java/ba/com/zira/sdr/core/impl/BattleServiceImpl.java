package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.MapState;
import ba.com.zira.sdr.api.model.battle.TeamStructure;
import ba.com.zira.sdr.api.model.battle.TeamsState;
import ba.com.zira.sdr.api.model.battle.TurnCombatState;
import ba.com.zira.sdr.core.mapper.BattleMapper;
import ba.com.zira.sdr.dao.BattleDAO;
import ba.com.zira.sdr.dao.BattleTurnDAO;
import ba.com.zira.sdr.dao.model.BattleEntity;
import ba.com.zira.sdr.dao.model.BattleTurnEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BattleServiceImpl implements BattleService {

    BattleMapper battleMapper;
    BattleDAO battleDAO;
    BattleTurnDAO battleTurnDAO;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PagedPayloadResponse<BattleResponse> find(FilterRequest request) throws ApiException {
        PagedData<BattleEntity> battleEntities = battleDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, battleEntities, battleMapper::entityToDTOs);
    }

    @Override
    public PayloadResponse<BattleGenerateResponse> create(final EntityRequest<BattleGenerateRequest> request)
            throws JsonProcessingException {
        var battleGenerateRequest = request.getEntity();
        System.out.println("BattleGenerateRequest  " + battleGenerateRequest);
        var battleEntity = battleMapper.dtoToEntity(battleGenerateRequest);

        battleEntity.setCreated(LocalDateTime.now());
        battleEntity.setCreatedBy(request.getUserId());
        battleEntity.setStatus(Status.DRAFT.value());
        battleEntity.setLastTurn(1L);

        battleDAO.persist(battleEntity);

        String mapStateJson = objectMapper.writeValueAsString(new MapState());

        TurnCombatState turnCombatState = new TurnCombatState();
        turnCombatState.setStatus(Status.DRAFT.value());
        String turnCombatStateJson = objectMapper.writeValueAsString(turnCombatState);

        TeamStructure teamStructure = new TeamStructure();
        teamStructure.setEligibleCountryIds(battleGenerateRequest.getCountries());

        TeamsState teamsState = new TeamsState();
        teamsState.setActivePlayerTeam(teamStructure);
        String teamStateJson = objectMapper.writeValueAsString(teamsState);

        BattleTurnEntity battleTurnEntity = new BattleTurnEntity();
        battleTurnEntity.setName(battleEntity.getName() + " - Turn " + battleEntity.getLastTurn());
        battleTurnEntity.setCreated(LocalDateTime.now());
        battleTurnEntity.setCreatedBy(battleEntity.getCreatedBy());
        battleTurnEntity.setStatus(battleEntity.getStatus());
        battleTurnEntity.setBattle(battleEntity);
        battleTurnEntity.setTurnNumber(0L);
        battleTurnEntity.setMapState(mapStateJson);
        battleTurnEntity.setTurnCombatState(turnCombatStateJson);
        battleTurnEntity.setTeamState(teamStateJson);

        battleTurnDAO.persist(battleTurnEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, battleMapper.entityToDtoOne(battleEntity));
    }

}