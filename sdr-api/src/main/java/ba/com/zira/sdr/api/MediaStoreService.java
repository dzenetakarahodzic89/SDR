package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;

public interface MediaStoreService {

    public PayloadResponse<String> delete(final EntityRequest<String> request) throws ApiException;

}
