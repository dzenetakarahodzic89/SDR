package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegration;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MusicMatchIntegrationStatistics;

// TODO: Auto-generated Javadoc
/**
 * The interface Morits integration service.
 */
public interface MoritsIntegrationService {

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<MoritsIntegration> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<MoritsIntegration> create(EntityRequest<MoritsIntegrationCreateRequest> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<MoritsIntegration> update(EntityRequest<MoritsIntegrationUpdateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

    /**
     * Gets the music match statistics.
     *
     * @param request
     *            the request
     * @return the music match statistics
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<MusicMatchIntegrationStatistics> getMusicMatchStatistics(EmptyRequest request) throws ApiException;
}
