package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;

/**
 * The interface Song artist service.
 */
public interface SongArtistService {

    /**
     * Get paged payload response.
     *
     * @param filterRequest
     *         the filter request
     * @return the paged payload response
     * @throws ApiException
     *         the api exception
     */
    PagedPayloadResponse<SongArtistResponse> get(final FilterRequest filterRequest) throws ApiException;

    /**
     * Create payload response.
     *
     * @param entityRequest
     *         the entity request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<SongArtistResponse> create(final EntityRequest<SongArtistCreateRequest> entityRequest) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param entityRequest
     *         the entity request
     * @return the payload response
     */
    PayloadResponse<String> delete(final EntityRequest<Long> entityRequest);

}
