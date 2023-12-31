package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.battle.Battle;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.api.model.battle.BattleResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleOverviewResponse;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.battle.BattleTurnUpdateRequest;
import ba.com.zira.sdr.api.model.battle.PreBattleCreateRequest;
import ba.com.zira.sdr.api.model.battle.WinnerCountryNamesResponse;

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

    ListPayloadResponse<BattleResponse> getAll(EmptyRequest request) throws ApiException;

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

    PayloadResponse<BattleGenerateResponse> create(EntityRequest<BattleGenerateRequest> request) throws ApiException;

    public PayloadResponse<String> preBattleTurn(EntityRequest<PreBattleCreateRequest> request) throws ApiException;

    public PayloadResponse<String> attackBattle(EntityRequest<BattleTurnUpdateRequest> request) throws ApiException;

    PayloadResponse<WinnerCountryNamesResponse> getWinnerLoserCountryName(EntityRequest<Long> request) throws ApiException;

}
