package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;

public interface ChordProgressionService {
    public PagedPayloadResponse<ChordProgressionResponse> find(final FilterRequest request) throws ApiException;

    PayloadResponse<ChordProgressionResponse> create(EntityRequest<ChordProgressionCreateRequest> request) throws ApiException;

    PayloadResponse<ChordProgressionResponse> delete(EntityRequest<Long> id) throws ApiException;

    PayloadResponse<ChordProgressionResponse> update(EntityRequest<ChordProgressionUpdateRequest> request) throws ApiException;
}
