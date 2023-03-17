package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.sdr.api.playlistga.PlaylistRequestGA;
import ba.com.zira.sdr.api.playlistga.PlaylistResponseGA;

public interface PlaylistGAService {
    PagedPayloadResponse<PlaylistResponseGA> generatePlaylist(final EntityRequest<PlaylistRequestGA> request) throws ApiException;
}
