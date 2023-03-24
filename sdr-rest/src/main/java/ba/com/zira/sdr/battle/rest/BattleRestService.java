package ba.com.zira.sdr.battle.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.battle.BattleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "battle", description = "Battle API")
@RestController
@RequestMapping(value = "battle")
@AllArgsConstructor

public class BattleRestService {

    @Autowired
    private BattleService battleService;

    @Operation(summary = "Find battle")
    @GetMapping
    public PagedPayloadResponse<BattleResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return battleService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

}
