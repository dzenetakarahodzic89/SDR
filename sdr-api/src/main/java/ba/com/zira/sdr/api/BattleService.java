package ba.com.zira.sdr.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;

/**
 * The Interface AlbumService.
 */
/**
 * @author User
 *
 */
/**
 * @author User
 *
 */
/**
 * @author User
 *
 */
public interface BattleService {

    PayloadResponse<BattleGenerateResponse> create(EntityRequest<BattleGenerateRequest> request) throws JsonProcessingException;

}
