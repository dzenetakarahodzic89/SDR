package ba.com.zira.sdr.battle.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.BattleService;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "battle", description = "Battle API")
@RestController
@RequestMapping(value = "battle")
@AllArgsConstructor
public class BattleRestService {

    BattleService battleService;

    @Operation(summary = "Create a battle")
    @PostMapping
    public PayloadResponse<BattleGenerateResponse> create(@RequestBody final BattleGenerateRequest battle) throws JsonProcessingException {
        return battleService.create(new EntityRequest<>(battle));
    }

}