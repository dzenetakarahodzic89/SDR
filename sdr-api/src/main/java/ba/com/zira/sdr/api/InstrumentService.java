package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;

/**
 * The interface Instrument service.
 */
public interface InstrumentService {

    /**
     * Get paged payload response.
     *
     * @param filterRequest
     *         the filter request
     * @return the paged payload response
     * @throws ApiException
     *         the api exception
     */
    PagedPayloadResponse<InstrumentResponse> get(final FilterRequest filterRequest) throws ApiException;

    /**
     * Create payload response.
     *
     * @param entityRequest
     *         the entity request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<InstrumentResponse> create(final EntityRequest<InstrumentCreateRequest> entityRequest) throws ApiException;

    /**
     * Update payload response.
     *
     * @param entityRequest
     *         the entity request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<InstrumentResponse> update(final EntityRequest<InstrumentUpdateRequest> entityRequest) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param entityRequest
     *         the entity request
     * @return the payload response
     */
    PayloadResponse<InstrumentResponse> delete(final EntityRequest<Long> entityRequest);

}
