package ba.com.zira.sdr.battle.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "battle")
@RestController
@RequestMapping(value = "battle")
@AllArgsConstructor
public class BattleRestService {
    @Autowired
    BattleService battleService;

    @Operation(summary = "Find battle")
    @GetMapping
    public PagedPayloadResponse<BattleResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return battleService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find battle by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<Battle> findById(@Parameter(required = true, description = "Id of the battle") @PathVariable final Long id)
            throws ApiException {
        return battleService.getById(new EntityRequest<>(id));
   	}
        
    @Operation(summary = "Get battle turn")
    @GetMapping(value = "{id}")
    public PayloadResponse<BattleSingleResponse> getById(
            @Parameter(required = true, description = "ID of the battle") @PathVariable final Long id) throws ApiException {
        return battleService.getLastTurn(new EntityRequest<>(id));
    }

}
