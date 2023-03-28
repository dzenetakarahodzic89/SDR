package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.person.PersonCountResponse;
import ba.com.zira.sdr.api.model.person.PersonCountryRequest;
import ba.com.zira.sdr.api.model.person.PersonCreateRequest;
import ba.com.zira.sdr.api.model.person.PersonOverviewResponse;
import ba.com.zira.sdr.api.model.person.PersonResponse;
import ba.com.zira.sdr.api.model.person.PersonSearchRequest;
import ba.com.zira.sdr.api.model.person.PersonSearchResponse;
import ba.com.zira.sdr.api.model.person.PersonUpdateRequest;

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

    public ListPayloadResponse<LoV> getPersonLoVs(EmptyRequest req) throws ApiException;

    PayloadResponse<PersonOverviewResponse> retrieveById(final EntityRequest<Long> request) throws ApiException;

    ListPayloadResponse<PersonSearchResponse> search(final EntityRequest<PersonSearchRequest> request) throws ApiException;

    PayloadResponse<PersonCountResponse> getPersonsByCountry(EmptyRequest req) throws ApiException;

    ListPayloadResponse<LoV> getPersonLoV(EmptyRequest req) throws ApiException;

}
