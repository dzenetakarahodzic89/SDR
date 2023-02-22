package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModel;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelUpdateRequest;

public interface SongSimilarityDetailService {

    public PagedPayloadResponse<SongSimilarityDetailModel> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<SongSimilarityDetailModel> create(EntityRequest<SongSimilarityDetailModelCreateRequest> request)
            throws ApiException;

    public PayloadResponse<SongSimilarityDetailModel> delete(EntityRequest<Long> request) throws ApiException;

    public PayloadResponse<SongSimilarityDetailModel> update(EntityRequest<SongSimilarityDetailModelUpdateRequest> request)
            throws ApiException;

}
