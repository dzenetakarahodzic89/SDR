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
import ba.com.zira.sdr.api.model.lov.LoV;

/**
 * The interface Genre service.
 */
public interface GenreService {

    /**
     * Find paged payload response.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<Genre> find(final FilterRequest request) throws ApiException;

    /**
     * Gets the genres over eras.
     *
     * @param request
     *            the request
     * @return the genres over eras
     * @throws ApiException
     *             the api exception
     */
    public ListPayloadResponse<GenreEraOverview> getGenresOverEras(EmptyRequest request) throws ApiException;

    /**
     * Gets the main genre LoV.
     *
     * @param request
     *            the request
     * @return main genre LoV
     * @throws ApiException
     *             the api exception
     */
    public ListPayloadResponse<LoV> getMainGenreLoV(EmptyRequest request) throws ApiException;

    /**
     * Gets the subgenre of main genre LoV.
     *
     * @param request
     *            the request
     * @return subgenre of main genre LoV
     * @throws ApiException
     *             the api exception
     */
    public ListPayloadResponse<LoV> getSubgenreLoV(EntityRequest<Long> request) throws ApiException;

    /**
     * Create payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Genre> create(EntityRequest<GenreCreateRequest> request) throws ApiException;

    /**
     * Update payload response.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Genre> update(EntityRequest<GenreUpdateRequest> request) throws ApiException;

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
     * Gets the sub genre main genre names.
     *
     * @param request
     *            the request
     * @return the sub genre main genre names
     * @throws ApiException
     *             the api exception
     */
    ListPayloadResponse<LoV> getSubGenreMainGenreNames(final EmptyRequest request) throws ApiException;

    ListPayloadResponse<LoV> getGenreLoVs(final EmptyRequest request) throws ApiException;
}
