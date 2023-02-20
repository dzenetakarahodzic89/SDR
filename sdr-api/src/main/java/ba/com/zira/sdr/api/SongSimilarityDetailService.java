package ba.com.zira.sdr.api;


import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.SampleModel;
import ba.com.zira.sdr.api.model.SampleModelCreateRequest;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModel;


public interface SongSimilarityDetailService {
 
    public PagedPayloadResponse<SongSimilarityDetailModel> find(final FilterRequest request) throws ApiException;

  
    PayloadResponse<SongSimilarityDetailModel> create(EntityRequest<SampleModelCreateRequest> request) throws ApiException;


    PayloadResponse<SongSimilarityDetailModel> update(EntityRequest<SampleModelUpdateRequest> request) throws ApiException;

  
    PayloadResponse<SongSimilarityDetailModel> activate(EntityRequest<Long> request) throws ApiException;
    
    PayloadResponse<SongSimilarityDetailModel> delete(EntityRequest<Long> request) throws ApiException;
    
    
 
}
