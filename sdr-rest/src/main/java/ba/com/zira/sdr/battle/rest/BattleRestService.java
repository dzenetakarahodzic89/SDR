package ba.com.zira.sdr.battle.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleOverviewResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.BattleTurnUpdateRequest;
import ba.com.zira.sdr.api.model.battle.PreBattleCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "battle")
@RestController
@RequestMapping(value = "battle")
@AllArgsConstructor
public class BattleRestService {

    BattleService battleService;

    @Operation(summary = "Find battle")
    @GetMapping
    public PagedPayloadResponse<BattleResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return battleService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Get all battle")
    @GetMapping(value = "/all")
    public ListPayloadResponse<BattleResponse> getAll() throws ApiException {
        return battleService.getAll(new EmptyRequest());
    }

    @Operation(summary = "Find battle by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<Battle> findById(@Parameter(required = true, description = "Id of the battle") @PathVariable final Long id)
            throws ApiException {
        return battleService.getById(new EntityRequest<>(id));
    }

    @Operation(summary = "Get battle turn")
    @GetMapping(value = "{id}/get-last-turn")
    public PayloadResponse<BattleSingleResponse> getById(
            @Parameter(required = true, description = "ID of the battle") @PathVariable final Long id) throws ApiException {
        return battleService.getLastTurn(new EntityRequest<>(id));
    }

    @Operation(summary = "Get battle turn")
    @GetMapping(value = "{id}/overview")
    public PayloadResponse<BattleSingleOverviewResponse> getOverviewById(
            @Parameter(required = true, description = "ID of the battle") @PathVariable final Long id) throws ApiException {
        return battleService.getSingleOverview(new EntityRequest<>(id));
    }

    @Operation(summary = "pre-battle check")
    @PostMapping(value = "pre-battle")
    public PayloadResponse<String> preBattleCheckAndTurn(@RequestBody final PreBattleCreateRequest request) throws ApiException {
        return battleService.preBattleTurn(new EntityRequest<>(request));
    }

    @Operation(summary = "attack-country")
    @PutMapping(value = "{battleTurnId}/start-battle")
    public PayloadResponse<String> attackCountry(@RequestBody final BattleTurnUpdateRequest request,
            @Parameter(required = true, description = "ID of the battle") @PathVariable final Long battleTurnId) throws ApiException {
        if (request.getBattleTurnId() == null) {
            request.setBattleTurnId(battleTurnId);
        }
        return battleService.attackBattle(new EntityRequest<>(request));
    }
}
