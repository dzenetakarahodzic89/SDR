package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMedia;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaCreateRequest;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaUpdateRequest;

public interface ConnectedMediaService {
    public PagedPayloadResponse<ConnectedMedia> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<ConnectedMedia> create(EntityRequest<ConnectedMediaCreateRequest> request) throws ApiException;

    public PayloadResponse<ConnectedMedia> update(EntityRequest<ConnectedMediaUpdateRequest> request) throws ApiException;

}
