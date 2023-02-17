package ba.com.zira.sdr.api;

import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;

public interface SongArtistService {

    PagedPayloadResponse<SongArtistResponse> getFiltered(FilterRequest filterRequest);

}
