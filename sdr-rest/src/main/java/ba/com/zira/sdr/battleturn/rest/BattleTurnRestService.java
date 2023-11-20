package ba.com.zira.sdr.battleturn.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.BattleTurnService;
import ba.com.zira.sdr.api.model.battle.ActivePlayerTeamUpdateRequest;
import ba.com.zira.sdr.api.model.battle.BattleUnfinishedTurnResponse;
import ba.com.zira.sdr.api.model.battle.EligibleArtistsInformation;
import ba.com.zira.sdr.api.model.battle.TeamInformation;
import ba.com.zira.sdr.api.model.battle.TeamStructure;
import ba.com.zira.sdr.api.model.battle.TurnCombatState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "battle-turn")
@RestController
@RequestMapping(value = "battle-turn")
public class BattleTurnRestService {
    @Autowired
    private BattleTurnService battleTurnService;

    @Operation(summary = "Get player team information by battle id")
    @GetMapping(value = "/team/{id}")
    public PayloadResponse<TeamInformation> getPlayingTeamByBattleId(
            @Parameter(required = true, description = "Id of the battle") @PathVariable final Long id) throws ApiException {
        return battleTurnService.getPlayingTeamByBattleId(new EntityRequest<>(id));
    }

    @Operation(summary = "Get artists and songs eligible for the team based on country ids")
    @PostMapping(value = "/eligible")
    public PayloadResponse<EligibleArtistsInformation> getEligibleArtistsInformation(
            @Parameter(required = true, description = "List of eligible country ids") @RequestBody final ArrayList<Long> countryIds)
            throws ApiException {
        return battleTurnService.getEligibleArtistsInformationByCountryId(new EntityRequest<>(countryIds));
    }

    @Operation(summary = "Update player team for battle")
    @PostMapping(value = "/update/team/{battleId}")
    public PayloadResponse<String> updatePlayerTeam(@Parameter(required = true, description = "Battle id") @PathVariable Long battleId,
            @Parameter(required = true, description = "Team structure") @RequestBody final TeamStructure team) throws ApiException {
        var request = new EntityRequest<>(new ActivePlayerTeamUpdateRequest(team, battleId));
        return battleTurnService.updateTeam(request);
    }

    @Operation(summary = "Get battle logs for turn")
    @GetMapping(value = "/get-log/{turnId}")
    public PayloadResponse<TurnCombatState> getBattleLogsForTurn(
            @Parameter(required = true, description = "Turn id") @PathVariable Long turnId) throws ApiException {
        return battleTurnService.getBattleLogs(new EntityRequest<>(turnId));
    }

    @GetMapping(value = "/get-random-unfinished")
    public PayloadResponse<BattleUnfinishedTurnResponse> getRandomUnfinishedBattleTurn() throws ApiException {
        return battleTurnService.getRandomUnfinishedBattleTurn(new EmptyRequest());
    }
}
