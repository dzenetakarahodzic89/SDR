package ba.com.zira.sdr.battle.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "battle", description = "Battle API")
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

    @Operation(summary = "Create a battle")
    @PostMapping
    public PayloadResponse<BattleGenerateResponse> create(@RequestBody final EntityRequest<BattleGenerateRequest> request)
            throws JsonProcessingException {

        return battleService.create(request);
    }

}
