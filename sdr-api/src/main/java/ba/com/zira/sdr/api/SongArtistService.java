package ba.com.zira.sdr.api;

import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;

public interface SongArtistService {

    ListPayloadResponse<SongArtistResponse> getAll(EmptyRequest request);

}
