package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreEraOverview;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;

/**
 * The interface Genre service.
 */
public interface GenreService {

    /**
     * Find paged payload response.
     *
     * @param request
     *         the request
     * @return the paged payload response
     * @throws ApiException
     *         the api exception
     */
    PagedPayloadResponse<Genre> find(final FilterRequest request) throws ApiException;


    public ListPayloadResponse<GenreEraOverview> getGenresOverEras(EmptyRequest request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<Genre> create(EntityRequest<GenreCreateRequest> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<Genre> update(EntityRequest<GenreUpdateRequest> request) throws ApiException;

    /**
     * Delete payload response.
     *
     * @param request
     *         the request
     * @return the payload response
     * @throws ApiException
     *         the api exception
     */
    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

}
