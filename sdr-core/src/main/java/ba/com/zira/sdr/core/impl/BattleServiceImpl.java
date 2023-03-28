package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ba.com.zira.sdr.api.model.battle.BattleLogEntry;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.CountryState;
import ba.com.zira.sdr.api.model.battle.MapState;
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
            CountryState countryState = new CountryState(country.getId(), country.getName(), 2L, (double) 0L, Status.ACTIVE.value());
            countryStates.add(countryState);
        }

        for (CountryEntity country : passiveCountries) {
            CountryState countryState = new CountryState(country.getId(), country.getName(), 1L, (double) -1L, Status.INACTIVE.value());
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
        BattleLog battleLog = new BattleLog(textHistory, battleLogEntity, null, null);

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

}