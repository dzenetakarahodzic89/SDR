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

public interface GenreService {

    public PagedPayloadResponse<Genre> find(final FilterRequest request) throws ApiException;

    public ListPayloadResponse<GenreEraOverview> getGenresOverEras(EmptyRequest request) throws ApiException;

    public PayloadResponse<Genre> create(EntityRequest<GenreCreateRequest> request) throws ApiException;

    public PayloadResponse<Genre> update(EntityRequest<GenreUpdateRequest> request) throws ApiException;

    public PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

}
