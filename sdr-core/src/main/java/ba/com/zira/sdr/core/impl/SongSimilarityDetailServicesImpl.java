package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.SongSimilarityDetailService;
import ba.com.zira.sdr.api.model.SampleModelCreateRequest;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModel;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongSimilarityDetailServiceImpl implements SongSimilarityDetailService {
    @Override
    public PagedPayloadResponse<SongSimilarityDetailModel> find(FilterRequest request) throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PayloadResponse<SongSimilarityDetailModel> create(EntityRequest<SampleModelCreateRequest> request) throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PayloadResponse<SongSimilarityDetailModel> update(EntityRequest<SampleModelUpdateRequest> request) throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PayloadResponse<SongSimilarityDetailModel> activate(EntityRequest<Long> request) throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PayloadResponse<SongSimilarityDetailModel> delete(EntityRequest<Long> request) throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

}
