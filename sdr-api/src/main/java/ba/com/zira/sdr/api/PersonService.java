package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.person.PersonCountryRequest;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;

// TODO: Auto-generated Javadoc
/**
 * The interface Person service.
 */
public interface PersonService {

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<PersonResponse> find(final FilterRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<PersonResponse> create(final EntityRequest<PersonCreateRequest> entityRequest) throws ApiException;

    /**
     * Update payload response.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<PersonResponse> update(final EntityRequest<PersonUpdateRequest> entityRequest) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param entityRequest
     *            the entity request
     * @return the payload response
     */
    PayloadResponse<String> delete(final EntityRequest<Long> entityRequest);

    /**
     * Find by id.
     *
     * @param request
     *            the request
     * @return the payload response
     */
    PayloadResponse<PersonResponse> findById(EntityRequest<Long> request);

    PayloadResponse<PersonResponse> updatePersonCountry(EntityRequest<PersonCountryRequest> request);

}
