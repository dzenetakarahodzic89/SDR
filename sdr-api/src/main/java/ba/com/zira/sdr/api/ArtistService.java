package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.artist.ArtistByEras;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;

/**
 * The interface Artist service.
 */
public interface ArtistService {

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<ArtistResponse> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<ArtistResponse> create(EntityRequest<ArtistCreateRequest> request) throws ApiException;

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
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<ArtistResponse> update(EntityRequest<ArtistUpdateRequest> request) throws ApiException;

    ListPayloadResponse<ArtistByEras> getArtistsByEras(EntityRequest<Long> request) throws ApiException;

    /**
     * Gets the artist names.
     *
     * @param request
     *            the request
     * @return the artist names
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<LoV> getArtistNames(EmptyRequest request) throws ApiException;

    PayloadResponse<ArtistResponse> createFromPerson(EntityRequest<Long> request) throws ApiException;

}
