package ba.com.zira.sdr.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.battle.BattleGenerateRequest;
import ba.com.zira.sdr.api.model.battle.BattleGenerateResponse;
import ba.com.zira.sdr.api.model.battle.BattleResponse;

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

    PayloadResponse<BattleGenerateResponse> create(EntityRequest<BattleGenerateRequest> request) throws JsonProcessingException;

}
