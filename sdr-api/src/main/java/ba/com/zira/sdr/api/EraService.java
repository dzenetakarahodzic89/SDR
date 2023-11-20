package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.era.EraCreateRequest;
import ba.com.zira.sdr.api.model.era.EraResponse;
import ba.com.zira.sdr.api.model.era.EraSearchRequest;
import ba.com.zira.sdr.api.model.era.EraSearchResponse;
import ba.com.zira.sdr.api.model.era.EraUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;

/**
 * The interface Era service.
 */
public interface EraService {
    /**
     * Find list payload response.
     *
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    public ListPayloadResponse<LoV> getEraLoVs(EmptyRequest req) throws ApiException;

    /**
     * Find.
     *
     * @param request
     *            the request
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<EraSearchResponse> find(final EntityRequest<EraSearchRequest> request) throws ApiException;

    /**
     * Find by id.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<EraResponse> findById(final EntityRequest<Long> request) throws ApiException;

    /**
     * Creates the.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<EraResponse> create(EntityRequest<EraCreateRequest> request) throws ApiException;

    /**
     * Update.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<EraResponse> update(EntityRequest<EraUpdateRequest> entityRequest) throws ApiException;
}
