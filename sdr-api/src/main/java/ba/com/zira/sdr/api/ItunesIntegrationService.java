package ba.com.zira.sdr.api;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationResponse;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationUpdateRequest;

public interface ItunesIntegrationService {
    /**
     * Get paged payload response.
     *
     * @param filterRequest
     *            the filter request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<ItunesIntegrationResponse> get(final FilterRequest filterRequest) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<ItunesIntegrationResponse> create(final EntityRequest<ItunesIntegrationCreateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<ItunesIntegrationResponse> update(final EntityRequest<ItunesIntegrationUpdateRequest> request) throws ApiException;

  
}
