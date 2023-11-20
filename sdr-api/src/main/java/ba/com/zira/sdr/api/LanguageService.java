package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.model.lov.LoV;

public interface LanguageService {
    ListPayloadResponse<LoV> get(EmptyRequest req) throws ApiException;
}
