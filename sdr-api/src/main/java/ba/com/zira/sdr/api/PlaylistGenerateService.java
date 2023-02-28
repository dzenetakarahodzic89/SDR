package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.sdr.api.model.song.Song;

public interface PlaylistGenerateService {
    PagedPayloadResponse<Song> generatePlaylist(FilterRequest request) throws ApiException;
}
