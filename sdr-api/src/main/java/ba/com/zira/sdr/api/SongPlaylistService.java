package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylist;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistCreateRequest;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;

public interface SongPlaylistService {

    public PagedPayloadResponse<SongPlaylist> find(final FilterRequest request) throws ApiException;

    public PayloadResponse<SongPlaylist> create(EntityRequest<SongPlaylistCreateRequest> request) throws ApiException;

    public PayloadResponse<SongPlaylist> update(EntityRequest<SongPlaylistUpdateRequest> request) throws ApiException;

    public PayloadResponse<String> delete(EntityRequest<Long> request) throws ApiException;

    PayloadResponse<String> deleteByPlaylistIdAndSongId(EntityRequest<SongPlaylistCreateRequest> req);

}
