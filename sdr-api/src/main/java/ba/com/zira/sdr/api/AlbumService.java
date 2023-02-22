package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;

public interface AlbumService {
    public PagedPayloadResponse<AlbumResponse> find(final FilterRequest request) throws ApiException;

    PayloadResponse<AlbumResponse> create(EntityRequest<AlbumCreateRequest> request) throws ApiException;

    PayloadResponse<AlbumResponse> update(EntityRequest<AlbumUpdateRequest> request) throws ApiException;

    PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;
}
