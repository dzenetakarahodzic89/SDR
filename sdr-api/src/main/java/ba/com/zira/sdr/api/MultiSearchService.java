package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse;
import ba.com.zira.sdr.api.model.wiki.WikiResponse;

public interface MultiSearchService {
    ListPayloadResponse<MultiSearchResponse> find(EntityRequest<String> request) throws ApiException;

    ListPayloadResponse<WikiResponse> findWiki(EmptyRequest req) throws ApiException;

    ListPayloadResponse<MultiSearchResponse> getAll(EmptyRequest req) throws ApiException;

    ListPayloadResponse<MultiSearchResponse> getRandom(EmptyRequest req) throws ApiException;

}
