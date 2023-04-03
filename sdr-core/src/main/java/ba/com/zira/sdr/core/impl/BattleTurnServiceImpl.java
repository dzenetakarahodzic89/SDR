package ba.com.zira.sdr.core.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.BattleTurnService;
import ba.com.zira.sdr.api.model.battle.ActivePlayerTeamUpdateRequest;
import ba.com.zira.sdr.api.model.battle.EligibleArtistsInformation;
import ba.com.zira.sdr.api.model.battle.TeamInformation;
import ba.com.zira.sdr.api.model.battle.TeamsState;
import ba.com.zira.sdr.api.model.battle.TurnCombatState;
import ba.com.zira.sdr.dao.BattleTurnDAO;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.SongDAO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleTurnServiceImpl implements BattleTurnService {
    @NonNull
    BattleTurnDAO battleTurnDAO;
    @NonNull
    SongDAO songDAO;
    @NonNull
    CountryDAO countryDAO;
    private N2bObjectMapper objectMapper = new N2bObjectMapper();

    @Override
    public PayloadResponse<TeamInformation> getPlayingTeamByBattleId(EntityRequest<Long> request) throws ApiException {
        N2bObjectMapper mapper = new N2bObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var json = battleTurnDAO.getTeamStateJSONByBattleId(request.getEntity());
        try {
            TeamsState teamState = mapper.readValue(json, TeamsState.class);
            var team = teamState.getActivePlayerTeam();
            TeamInformation teamInformation = new TeamInformation();
            teamInformation.setTeamStructure(team);
            if (team != null && team.getTeamArtists() != null) {
                teamInformation.setAllArtistSongs(songDAO
                        .getAllSongsForArtists(team.getTeamArtists().stream().map(a -> a.getArtistId()).collect(Collectors.toList())));
            }
            return new PayloadResponse<>(request, ResponseCode.OK, teamInformation);
        } catch (JsonProcessingException e) {
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, null);
        }
    }

    @Override
    public PayloadResponse<EligibleArtistsInformation> getEligibleArtistsInformationByCountryId(EntityRequest<ArrayList<Long>> request)
            throws ApiException {
        var artists = battleTurnDAO.getEligibleArtistsInformationByCountryId(request.getEntity());
        var songs = songDAO.getAllSongsForArtists(artists.stream().map(a -> a.getArtistId()).collect(Collectors.toList()));
        var eligibleArtistsInformation = new EligibleArtistsInformation(artists, songs);
        return new PayloadResponse<>(request, ResponseCode.OK, eligibleArtistsInformation);
    }

    @Override
    public PayloadResponse<String> updateTeam(EntityRequest<ActivePlayerTeamUpdateRequest> request) throws ApiException {
        N2bObjectMapper mapper = new N2bObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var json = battleTurnDAO.getTeamStateJSONByBattleId(request.getEntity().getBattleId());
        try {
            TeamsState teamState = mapper.readValue(json, TeamsState.class);
            teamState.setActivePlayerTeam(request.getEntity().getTeamStructure());
            var teamStateJSON = mapper.writeValueAsString(teamState);
            battleTurnDAO.updateTeamStateOfLastTurn(teamStateJSON, request.getEntity().getBattleId(), request.getUserId());
            return new PayloadResponse<>(request, ResponseCode.OK,
                    "Player team of battle with id " + request.getEntity().getBattleId() + " updated!");
        } catch (Exception e) {
            e.printStackTrace();
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID,
                    "Error updating player team of battle with " + request.getEntity().getBattleId());
        }
    }

    @Override
    public PayloadResponse<TurnCombatState> getBattleLogs(EntityRequest<Long> request) throws ApiException {
        var found = battleTurnDAO.findByPK(request.getEntity()).getTurnCombatState();
        TurnCombatState result = null;
        try {
            result = objectMapper.readValue(found, TurnCombatState.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, null);
        }
        return new PayloadResponse<>(request, ResponseCode.OK, result);
    }
}
