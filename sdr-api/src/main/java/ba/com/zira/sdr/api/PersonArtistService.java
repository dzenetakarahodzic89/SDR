package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.personartist.PersonArtistCreateRequest;
import ba.com.zira.sdr.api.model.personartist.PersonArtistResponse;

/**
 * The interface Person artist service.
 */
public interface PersonArtistService {

    /**
     * Get paged payload response.
     *
     * @param filterRequest
     *         the filter request
     * @return the paged payload response
     * @throws ApiException
     *         the api exception
     */
    PagedPayloadResponse<PersonArtistResponse> get(final FilterRequest filterRequest) throws ApiException;

    /**
     * Create payload response.
     *
     * @param entityRequest
     *         the entity request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<PersonArtistResponse> create(final EntityRequest<PersonArtistCreateRequest> entityRequest) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param entityRequest
     *         the entity request
     * @return the payload response
     */
    PayloadResponse<String> delete(final EntityRequest<Long> entityRequest);

}
