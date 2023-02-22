package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;

public interface LyricService {

    public PagedPayloadResponse<Lyric> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<Lyric> create(EntityRequest<LyricCreateRequest> request) throws ApiException;

    public PayloadResponse<Lyric> update(EntityRequest<LyricUpdateRequest> request) throws ApiException;

    public PayloadResponse<Lyric> delete(EntityRequest<Long> request) throws ApiException;

}
