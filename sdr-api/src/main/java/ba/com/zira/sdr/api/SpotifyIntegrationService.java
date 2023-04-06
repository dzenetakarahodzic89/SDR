package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationStatistics;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;

// TODO: Auto-generated Javadoc
/**
 * The interface Spotify integration service.
 */
public interface SpotifyIntegrationService {

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<SpotifyIntegrationResponse> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<SpotifyIntegrationResponse> create(final EntityRequest<SpotifyIntegrationCreateRequest> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<SpotifyIntegrationResponse> update(final EntityRequest<SpotifyIntegrationUpdateRequest> request) throws ApiException;

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
     * Gets the data for statistics.
     *
     * @param request
     *            the request
     * @return the data for statistics
     */
    PayloadResponse<SpotifyIntegrationStatistics> getDataForStatistics(EmptyRequest request);
}
