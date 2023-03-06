package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateReq;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailResponse;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;

/**
 * The Interface SongSimilarityDetailService.
 */
public interface SongSimilarityDetailService {

    public PagedPayloadResponse<SongSimilarityDetail> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<SongSimilarityDetail> create(EntityRequest<SongSimilarityDetailCreateRequest> request) throws ApiException;

    public PayloadResponse<SongSimilarityDetail> delete(EntityRequest<Long> request) throws ApiException;

    public PayloadResponse<SongSimilarityDetail> update(EntityRequest<SongSimilarityDetailUpdateRequest> request) throws ApiException;

    PayloadResponse<SongSimilarityDetail> createSongSimilarityDetail(EntityRequest<SongSimilarityDetailCreateReq> request);

    ListPayloadResponse<SongSimilarityDetailResponse> getAll(EntityRequest<Long> request) throws ApiException;

    public PagedPayloadResponse<SongSimilarityDetail> get(final FilterRequest filterRequest) throws ApiException;

}
