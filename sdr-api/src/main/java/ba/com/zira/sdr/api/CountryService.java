package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.country.CountriesSearchRequest;
import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryGetByIdsRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;

/**
 * The interface Country service.
 */
public interface CountryService {
    /**
     * Get paged payload response.
     *
     * @param filterRequest
     *            the filter request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<CountryResponse> get(final FilterRequest filterRequest) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<CountryResponse> create(final EntityRequest<CountryCreateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<CountryResponse> update(final EntityRequest<CountryUpdateRequest> request) throws ApiException;

    ListPayloadResponse<CountryResponse> getAll(EmptyRequest req) throws ApiException;

    ListPayloadResponse<LoV> getAllCountriesExceptOneWithTheSelectedId(final EntityRequest<CountriesSearchRequest> request)
            throws ApiException;

    ListPayloadResponse<LoV> getAllCountryLoVsByIds(EntityRequest<CountryGetByIdsRequest> request) throws ApiException;
}
