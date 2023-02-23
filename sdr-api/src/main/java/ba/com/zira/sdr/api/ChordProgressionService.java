package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;

/**
 * The interface Chord progression service.
 */
public interface ChordProgressionService {
    /**
     * Find paged payload response.
     *
     * @param request
     *         the request
     * @return the paged payload response
     * @throws ApiException
     *         the api exception
     */
    PagedPayloadResponse<ChordProgressionResponse> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<ChordProgressionResponse> create(EntityRequest<ChordProgressionCreateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param id
     *         the id
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<String> delete(EntityRequest<Long> id) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<ChordProgressionResponse> update(EntityRequest<ChordProgressionUpdateRequest> request) throws ApiException;
}
