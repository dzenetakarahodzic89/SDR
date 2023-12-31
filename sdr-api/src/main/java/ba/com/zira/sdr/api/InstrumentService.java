package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.request.ListRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.instrument.InsertSongInstrumentRequest;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentSearchRequest;
import ba.com.zira.sdr.api.instrument.InstrumentSearchResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.api.instrument.ResponseSongInstrument;
import ba.com.zira.sdr.api.instrument.ResponseSongInstrumentEra;

/**
 * The interface Instrument service.
 */
public interface InstrumentService {

    /**
     * Get paged payload response.
     *
     * @param filterRequest
     *            the filter request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<InstrumentResponse> find(final FilterRequest filterRequest) throws ApiException;

    /**
     * Gets the.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<InstrumentResponse> get(final EntityRequest<Long> request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<InstrumentResponse> create(final EntityRequest<InstrumentCreateRequest> entityRequest) throws ApiException;

    /**
     * Update payload response.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<InstrumentResponse> update(final EntityRequest<InstrumentUpdateRequest> entityRequest) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     */
    PayloadResponse<String> delete(final EntityRequest<Long> entityRequest);

    /**
     * Insert instruments to song.
     *
     * @param entityRequest
     *            the entity request
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<ResponseSongInstrument> insertInstrumentsToSong(ListRequest<InsertSongInstrumentRequest> entityRequest)
            throws ApiException;

    /**
     * Find all songs in eras for instruments.
     *
     * @param request
     *            the request
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<ResponseSongInstrumentEra> findAllSongsInErasForInstruments(EntityRequest<Long> request) throws ApiException;

    /**
     * Search.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<InstrumentSearchResponse> instrumentSearch(final EntityRequest<InstrumentSearchRequest> request)
            throws ApiException;

}
