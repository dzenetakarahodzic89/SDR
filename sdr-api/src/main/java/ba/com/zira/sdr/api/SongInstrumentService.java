package ba.com.zira.sdr.api;


import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrument;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentCreateRequest;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentUpdateRequest;

/**
 * * Methods used to manipulate {@link SongInstrument} data. <br>
 * List of APIs implemented in this class with links:
 * <ul>
 * <li>{@link #find}</li>
 * <li>{@link #create}</li>
 * 
 * </ul>
 * 
 * @author zira
 *
 */
public interface SongInstrumentService {

    
    public PagedPayloadResponse<SongInstrument> find(final FilterRequest request) throws ApiException;

    
    PayloadResponse<SongInstrument> create(EntityRequest<SongInstrumentCreateRequest> request) throws ApiException;
    PayloadResponse<SongInstrument> delete(EntityRequest<Long> request) throws ApiException;

    public PayloadResponse<SongInstrument> update(EntityRequest<SongInstrumentUpdateRequest> entityRequest) throws ApiException;
}