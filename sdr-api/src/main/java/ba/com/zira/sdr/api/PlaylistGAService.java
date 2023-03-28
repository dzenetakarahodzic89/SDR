package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.sdr.api.model.playlistga.GAHistoryResponse;
import ba.com.zira.sdr.api.model.playlistga.PlaylistRequestGA;
import ba.com.zira.sdr.api.model.playlistga.PlaylistResponseGA;

public interface PlaylistGAService {
    PagedPayloadResponse<PlaylistResponseGA> generatePlaylist(final EntityRequest<PlaylistRequestGA> request) throws ApiException;

    PagedPayloadResponse<GAHistoryResponse> getHistory(FilterRequest filterRequest) throws ApiException;
}
