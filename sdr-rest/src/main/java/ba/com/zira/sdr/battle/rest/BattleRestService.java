package ba.com.zira.sdr.battle.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.Battle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "battle")
@RestController
@RequestMapping(value = "battle")
public class BattleRestService {
    @Autowired
    private BattleService battleService;

    @Operation(summary = "Find battle by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<Battle> findById(@Parameter(required = true, description = "Id of the battle") @PathVariable final Long id)
            throws ApiException {
        return battleService.getById(new EntityRequest<>(id));
    }

}
