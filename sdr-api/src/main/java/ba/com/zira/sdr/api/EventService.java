package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.sdr.api.model.event.EventResponse;

public interface EventService {
    PagedPayloadResponse<EventResponse> find(final FilterRequest request) throws ApiException;
}
