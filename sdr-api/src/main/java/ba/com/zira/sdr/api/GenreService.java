package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;

public interface GenreService {

    public PagedPayloadResponse<Genre> find(final FilterRequest request) throws ApiException;

    PayloadResponse<Genre> create(EntityRequest<GenreCreateRequest> request) throws ApiException;

    PayloadResponse<Genre> update(EntityRequest<GenreUpdateRequest> request) throws ApiException;

    PayloadResponse<Genre> activate(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<Genre> delete(EntityRequest<Long> request) throws ApiException;

}
