package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.song.fft.SongFftResult;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;

/**
 * The interface Song fft result service.
 */
public interface SongFftResultService {

    /**
     * Find paged payload response.
     *
     * @param request
     *         the request
     * @return the paged payload response
     * @throws ApiException
     *         the api exception
     */
    PagedPayloadResponse<SongFftResult> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongFftResult> create(EntityRequest<SongFftResultCreateRequest> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongFftResult> update(EntityRequest<SongFftResultUpdateRequest> request) throws ApiException;

    /**
     * Activate payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongFftResult> activate(EntityRequest<Long> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongFftResult> delete(EntityRequest<Long> request) throws ApiException;

}
