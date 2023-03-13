package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionByEraResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionSearchRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionSearchResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;

/**
 * The interface Chord progression service.
 */
public interface ChordProgressionService {
    /**
     * Find list payload response.
     *
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<ChordProgressionByEraResponse> getChordByEras(EmptyRequest req) throws ApiException;

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<ChordProgressionResponse> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<ChordProgressionResponse> create(EntityRequest<ChordProgressionCreateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param id
     *            the id
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(EntityRequest<Long> id) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<ChordProgressionResponse> update(EntityRequest<ChordProgressionUpdateRequest> request) throws ApiException;

    /**
     * Gets chord progression LoV.
     *
     * @param request
     *            the request
     * @return the chord progression LoV
     * @throws ApiException
     *             the api exception
     */
    public ListPayloadResponse<LoV> getChordProgressionLoV(EmptyRequest request) throws ApiException;

    ListPayloadResponse<ChordProgressionSearchResponse> searchChordProgression(final EntityRequest<ChordProgressionSearchRequest> request)
            throws ApiException;

}
