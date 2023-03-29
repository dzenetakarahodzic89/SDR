package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleOverviewResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;

/**
 * The Interface BattleService.
 */
public interface BattleService {
    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<BattleResponse> find(final FilterRequest request) throws ApiException;

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

    PayloadResponse<BattleSingleResponse> getLastTurn(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<BattleSingleOverviewResponse> getSingleOverview(EntityRequest<Long> request) throws ApiException;
}
