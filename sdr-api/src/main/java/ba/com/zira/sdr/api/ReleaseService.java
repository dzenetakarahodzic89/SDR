package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.sdr.api.model.release.ReleaseSearchRequest;
import ba.com.zira.sdr.api.model.release.ReleaseSearchResponse;

public interface ReleaseService {

    PagedPayloadResponse<ReleaseSearchResponse> find(SearchRequest<ReleaseSearchRequest> request) throws ApiException;

}
