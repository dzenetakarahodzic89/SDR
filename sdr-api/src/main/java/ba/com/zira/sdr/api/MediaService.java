package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectRequest;
import ba.com.zira.sdr.api.model.media.MediaObjectResponse;
import ba.com.zira.sdr.api.model.media.MediaResponse;

public interface MediaService {

    public PayloadResponse<String> save(EntityRequest<MediaCreateRequest> request) throws ApiException;

    public PagedPayloadResponse<MediaResponse> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<MediaObjectResponse> findByIdAndObjectType(EntityRequest<MediaObjectRequest> request) throws ApiException;

    public PayloadResponse<String> create(final EntityRequest<MediaCreateRequest> request) throws ApiException;

}
