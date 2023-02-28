package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.model.lov.LoV;

/**
 * The interface Era service.
 */
public interface EraService {
    /**
     * Find list payload response.
     *
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    public ListPayloadResponse<LoV> getEraLoVs(EmptyRequest req) throws ApiException;
}
