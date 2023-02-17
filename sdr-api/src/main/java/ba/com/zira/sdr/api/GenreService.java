package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.genre.GenreModel;
import ba.com.zira.sdr.api.model.genre.GenreModelCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreModelUpdateRequest;

public interface GenreService {

    public PagedPayloadResponse<GenreModel> find(final FilterRequest request) throws ApiException;

    PayloadResponse<GenreModel> create(EntityRequest<GenreModelCreateRequest> request) throws ApiException;

    PayloadResponse<GenreModel> update(EntityRequest<GenreModelUpdateRequest> request) throws ApiException;

    PayloadResponse<GenreModel> activate(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<GenreModel> delete(EntityRequest<Long> request) throws ApiException;

}
