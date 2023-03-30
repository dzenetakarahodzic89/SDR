package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.model.battle.ArtistStructure;
import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.api.model.battle.BattleLog;
import ba.com.zira.sdr.api.model.battle.BattleLogBattleResult;
import ba.com.zira.sdr.api.model.battle.BattleLogEntry;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.BattleTurnUpdateRequest;
import ba.com.zira.sdr.api.model.battle.CountryState;
import ba.com.zira.sdr.api.model.battle.MapState;
import ba.com.zira.sdr.api.model.battle.PreBattleCreateRequest;
import ba.com.zira.sdr.api.model.battle.SongStructure;
import ba.com.zira.sdr.api.model.battle.TeamStructure;
import ba.com.zira.sdr.api.model.battle.TeamsState;
import ba.com.zira.sdr.api.model.battle.TurnCombatState;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.core.mapper.BattleMapper;
import ba.com.zira.sdr.dao.BattleDAO;
import ba.com.zira.sdr.dao.BattleTurnDAO;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.model.BattleEntity;
import ba.com.zira.sdr.dao.model.BattleTurnEntity;
import ba.com.zira.sdr.dao.model.CountryEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    @NonNull
    BattleDAO battleDAO;
    @NonNull
    CountryDAO countryDAO;

    @NonNull
    BattleMapper battleMapper;
    @NonNull
    BattleTurnDAO battleTurnDAO;
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
    public PayloadResponse<BattleGenerateResponse> create(final EntityRequest<BattleGenerateRequest> request) throws ApiException {

        var battleGenerateRequest = request.getEntity();
        var battleEntity = battleMapper.dtoToEntity(battleGenerateRequest);

        battleEntity.setCreated(LocalDateTime.now());
        battleEntity.setCreatedBy(request.getUserId());
        battleEntity.setStatus("In progress");
        battleEntity.setLastTurn(1L);

        battleDAO.persist(battleEntity);

        List<Long> activeCountries = battleDAO.getActiveCountries(battleGenerateRequest.getTeamSize());
        List<CountryResponse> numberOfActiveCountries = battleDAO.getNumberOfActiveCountries(activeCountries,
                battleGenerateRequest.getSongSize());

        List<CountryEntity> allCountries = countryDAO.findAll();
        List<CountryEntity> passiveCountries = new ArrayList<>();

        for (CountryEntity country : allCountries) {
            boolean isActive = false;
            for (CountryResponse activeCountry : numberOfActiveCountries) {
                if (country.getId() == activeCountry.getId()) {
                    isActive = true;
                    break;
                }
            }
            if (!isActive) {
                passiveCountries.add(country);
            }
        }

        List<CountryState> countryStates = new ArrayList<>();
        for (CountryResponse country : numberOfActiveCountries) {
            if (battleGenerateRequest.getCountries().contains(country.getId())) {
                CountryState countryState = new CountryState(country.getId(), country.getName(), 1L, (double) 0L, Status.ACTIVE.value());
                countryStates.add(countryState);
            } else {
                CountryState countryState = new CountryState(country.getId(), country.getName(), 2L, (double) 0L, Status.ACTIVE.value());
                countryStates.add(countryState);
            }
        }

        for (CountryEntity country : passiveCountries) {
            CountryState countryState = new CountryState(country.getId(), country.getName(), 0L, (double) -1L, "Passive");
            countryStates.add(countryState);
        }

        var mapState = new MapState(countryStates, 1L, (long) numberOfActiveCountries.size(), (long) numberOfActiveCountries.size(), 0L,
                (long) passiveCountries.size());
        String mapStateJson = null;
        try {
            mapStateJson = objectMapper.writeValueAsString(mapState);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<Long, String> textHistory = new HashMap<>();
        List<BattleLogEntry> battleLogEntity = new ArrayList<>();

        List<BattleLogBattleResult> battleLogBattleResult = new ArrayList<>();

        BattleLog battleLog = new BattleLog(textHistory, battleLogEntity, battleLogBattleResult);

        var turnCombatState = new TurnCombatState("In Progress", Collections.singletonList(battleLog));
        String turnCombatStateJson = null;
        try {
            turnCombatStateJson = objectMapper.writeValueAsString(turnCombatState);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        var teamStructure = new TeamStructure();
        List<ArtistStructure> artistStructureList = new ArrayList<>();
        List<TeamStructure> activeNpcTeams = new ArrayList<>();

        teamStructure.setTeamArtists(artistStructureList);
        teamStructure.setId(1L);

        boolean active = false;
        for (Long countryId : activeCountries) {
            if (battleGenerateRequest.getCountries().contains(countryId)) {
                if (teamStructure.getCountryId() == null) {
                    teamStructure.setCountryId(countryId);
                    CountryEntity countryName = countryDAO.findByPK(teamStructure.getCountryId());
                    teamStructure.setCountryName(countryName.getName());
                }
                continue;
            }

            if (countryId != teamStructure.getCountryId()) {
                TeamStructure newTeamStructure = new TeamStructure();
                newTeamStructure.setId(2L);
                newTeamStructure.setCountryId(countryId);
                CountryEntity countryName = countryDAO.findByPK(newTeamStructure.getCountryId());
                newTeamStructure.setCountryName(countryName.getName());
                newTeamStructure.setNumberOfWins(0L);
                newTeamStructure.setNumberOfLoses(0L);
                newTeamStructure.setLastActiveTurn(0L);
                List<Long> eligibleCountryId = new ArrayList<>();
                eligibleCountryId.add(countryId);

                newTeamStructure.setEligibleCountryIds(eligibleCountryId);
                List<ArtistStructure> newArtistStructureList = new ArrayList<>();
                List<ArtistResponse> artistEntity = countryDAO.randomArtists(countryId, battleGenerateRequest.getTeamSize(),
                        battleGenerateRequest.getSongSize());
                for (ArtistResponse artistResponse : artistEntity) {
                    ArtistStructure artistStructure = new ArtistStructure();
                    artistStructure.setArtistId(artistResponse.getId());
                    artistStructure.setName(artistResponse.getName());
                    artistStructure.setCountryId(countryId);
                    artistStructure.setCountryName(countryName.getName());
                    List<SongResponse> songEntity = countryDAO.randomSongs(artistStructure.getArtistId(),
                            battleGenerateRequest.getSongSize());
                    List<SongStructure> songStructureList = new ArrayList<>();

                    for (SongResponse songResponse : songEntity) {
                        SongStructure songStructure = new SongStructure(songResponse.getId(), songResponse.getName(),
                                songResponse.getSpotifyId(), "");

                        songStructureList.add(songStructure);
                    }

                    artistStructure.setSongs(songStructureList);
                    newArtistStructureList.add(artistStructure);

                }
                if (!newArtistStructureList.isEmpty()) {
                    newTeamStructure.setTeamArtists(newArtistStructureList);
                    activeNpcTeams.add(newTeamStructure);
                }

            }

        }

        teamStructure.setNumberOfWins(0L);
        teamStructure.setNumberOfLoses(0L);
        teamStructure.setLastActiveTurn(0L);
        teamStructure.setEligibleCountryIds(battleGenerateRequest.getCountries());

        List<TeamStructure> inactiveNpcTeams = new ArrayList<>();
        var teamsState = new TeamsState(teamStructure, activeNpcTeams, inactiveNpcTeams);
        String teamStateJson = null;
        try {
            teamStateJson = objectMapper.writeValueAsString(teamsState);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        var battleTurnEntity = new BattleTurnEntity();
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
                    return new PayloadResponse<>(request, ResponseCode.OK,
                            "Country " + move.getAttackedName() + " is passive, " + move.getAttackerName() + " has taken over!");
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
            var savedTurn = battleTurnDAO.persist(newRequest);
            return new PayloadResponse<>(request, ResponseCode.OK, savedTurn.getId().toString());
        }
        return null;
    }

    @Override
    public PayloadResponse<String> attackBattle(EntityRequest<BattleTurnUpdateRequest> request) throws ApiException {
        var inProgressTurn = battleTurnDAO.findByPK(request.getEntity().getBattleTurnId());
        var requestEntity = request.getEntity();
        var loggedUser = request.getUser().getUserId();
        MapState mapState = null;
        TeamsState teamState = null;
        TurnCombatState combatState = null;
        var returnString = "";
        try {
            mapState = objectMapper.readValue(inProgressTurn.getMapState(), MapState.class);
            teamState = objectMapper.readValue(inProgressTurn.getTeamState(), TeamsState.class);
            combatState = objectMapper.readValue(inProgressTurn.getTurnCombatState(), TurnCombatState.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw ApiException.createFrom(request, ResponseCode.INVALID_SETUP, "Json could not be parsed");
        }
        combatState.setStatus("Finished");
        Long prevKey = combatState.getBattleLogs().get(0).getTextHistory().keySet().stream().max(Long::compare).orElse(0L);
        combatState.getBattleLogs().get(0).getTextHistory().put(prevKey + 1,
                "The battle is between " + requestEntity.getAttackerName() + " and " + requestEntity.getAttackedName());
        prevKey += 1;
        for (var explanation : requestEntity.getSongBattleExplained()) {
            combatState.getBattleLogs().get(0).getTextHistory().put(prevKey + 1, explanation + " decided by " + loggedUser);
            prevKey += 1;
        }
        combatState.getBattleLogs().get(0).getTurnHistory().addAll(requestEntity.getSongBattles());
        var foundNpcTeamIndex = 0;
        for (var i = 0; i < teamState.getActiveNpcTeams().size(); i++) {
            var item = teamState.getActiveNpcTeams().get(i);
            if (item.getCountryId().equals(requestEntity.getAttackedCountryId())) {
                foundNpcTeamIndex = i;
                break;
            }
        }
        if (requestEntity.getWonCase().equalsIgnoreCase("PLAYER")) {
            teamState.getActivePlayerTeam().setNumberOfWins(teamState.getActivePlayerTeam().getNumberOfWins() + 1);
            teamState.getActiveNpcTeams().get(foundNpcTeamIndex)
                    .setNumberOfLoses(teamState.getActiveNpcTeams().get(foundNpcTeamIndex).getNumberOfLoses() + 1);
            teamState.getActivePlayerTeam().getEligibleCountryIds().add(requestEntity.getAttackedCountryId());
            TeamStructure wonAgainsTeam = teamState.getActiveNpcTeams().get(foundNpcTeamIndex);
            teamState.getInactiveNpcTeams().add(wonAgainsTeam);
            teamState.getActiveNpcTeams().remove(foundNpcTeamIndex);
            Integer npcCountryIndex = null;
            for (var i = 0; i < mapState.getCountries().size(); i++) {
                if (mapState.getCountries().get(i).getCountryId().equals(requestEntity.getAttackedCountryId())) {
                    npcCountryIndex = i;
                    break;
                }
            }
            mapState.getCountries().get(npcCountryIndex).setTeamOwnershipId(1L);
            mapState.getCountries().get(npcCountryIndex).setMapValue((double) 1);
            returnString = "Country " + request.getEntity().getAttackerName() + " has won over " + request.getEntity().getAttackedName();

        } else if (requestEntity.getWonCase().equalsIgnoreCase("NPC")) {
            teamState.getActivePlayerTeam().setNumberOfLoses(teamState.getActivePlayerTeam().getNumberOfLoses() + 1);
            teamState.getActiveNpcTeams().get(foundNpcTeamIndex)
                    .setNumberOfWins(teamState.getActiveNpcTeams().get(foundNpcTeamIndex).getNumberOfWins() + 1);
            teamState.getActiveNpcTeams().get(foundNpcTeamIndex).getEligibleCountryIds().add(requestEntity.getAttackerCountryId());
            returnString = "Country " + request.getEntity().getAttackedName() + " has won over " + request.getEntity().getAttackerName();
        }

        inProgressTurn.setStatus("Finished");
        try {
            inProgressTurn.setTeamState(objectMapper.writeValueAsString(teamState));
            inProgressTurn.setTurnCombatState(objectMapper.writeValueAsString(combatState));
            inProgressTurn.setMapState(objectMapper.writeValueAsString(mapState));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        battleTurnDAO.merge(inProgressTurn);
        return new PayloadResponse<>(request, ResponseCode.OK, returnString);
    }

}
