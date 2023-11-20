package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryResponse;

/**
 * The interface Multi Search History service.
 */
public interface MultiSearchHistoryService {
    /**
     * Create payload response.
     *
     * @return the list payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<String> create(EmptyRequest req) throws ApiException;

    PayloadResponse<MultiSearchHistoryResponse> getLast(EmptyRequest req) throws ApiException;

    ListPayloadResponse<MultiSearchHistoryResponse> getAllOrderedByRefreshTime(EmptyRequest request) throws ApiException;
}
