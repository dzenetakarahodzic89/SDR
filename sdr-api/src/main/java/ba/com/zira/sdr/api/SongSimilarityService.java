package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarity;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityCreateRequest;
import ba.com.zira.sdr.api.model.songSimilarity.SongSimilarityResponse;

/**
 * The Interface SongSimilarityService
 */
public interface SongSimilarityService {

    PayloadResponse<SongSimilarity> create(EntityRequest<SongSimilarityCreateRequest> request) throws ApiException;

    ListPayloadResponse<SongSimilarityResponse> getAll(EmptyRequest req) throws ApiException;

}
