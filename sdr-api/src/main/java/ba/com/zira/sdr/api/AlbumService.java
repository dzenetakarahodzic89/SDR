package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.SampleModel;
import ba.com.zira.sdr.api.model.SampleModelCreateRequest;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;

public interface AlbumService {
    public PagedPayloadResponse<SampleModel> find(final FilterRequest request) throws ApiException;

    PayloadResponse<SampleModel> create(EntityRequest<SampleModelCreateRequest> request) throws ApiException;

    PayloadResponse<SampleModel> update(EntityRequest<SampleModelUpdateRequest> request) throws ApiException;

    PayloadResponse<SampleModel> activate(EntityRequest<Long> request) throws ApiException;
}
