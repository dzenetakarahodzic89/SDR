package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.generateplaylist.SavePlaylistRequest;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.song.Song;

public interface PlaylistGenerateService {
    PagedPayloadResponse<Song> generatePlaylist(final FilterRequest request) throws ApiException;

    PayloadResponse<Playlist> saveGeneratedPlaylist(final EntityRequest<SavePlaylistRequest> entityRequest) throws ApiException;
}
