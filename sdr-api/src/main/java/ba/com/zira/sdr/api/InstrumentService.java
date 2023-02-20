package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;

public interface InstrumentService {

    PagedPayloadResponse<InstrumentResponse> get(final FilterRequest filterRequest) throws ApiException;

    PayloadResponse<InstrumentResponse> create(final EntityRequest<InstrumentCreateRequest> entityRequest) throws ApiException;

    PayloadResponse<InstrumentResponse> update(final EntityRequest<InstrumentUpdateRequest> entityRequest) throws ApiException;

    PayloadResponse<InstrumentResponse> delete(final EntityRequest<Long> entityRequest);

}
