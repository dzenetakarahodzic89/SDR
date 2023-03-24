package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.battle.Battle;

/**
 * The Interface BattleService.
 */
public interface BattleService {

    /**
     * Gets the by id.
     *
     * @param request
     *            the request
     * @return the by id
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Battle> getById(EntityRequest<Long> request) throws ApiException;
}
