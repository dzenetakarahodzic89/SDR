package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResult;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResultUpdateRequest;

public interface SongFftResultService {

    public PagedPayloadResponse<SongFftResult> find(final FilterRequest request) throws ApiException;

    PayloadResponse<SongFftResult> create(EntityRequest<SongFftResultCreateRequest> request) throws ApiException;

    PayloadResponse<SongFftResult> update(EntityRequest<SongFftResultUpdateRequest> request) throws ApiException;

    PayloadResponse<SongFftResult> activate(EntityRequest<Long> request) throws ApiException;
    PayloadResponse<SongFftResult> delete(EntityRequest<Long> request) throws ApiException;

}
