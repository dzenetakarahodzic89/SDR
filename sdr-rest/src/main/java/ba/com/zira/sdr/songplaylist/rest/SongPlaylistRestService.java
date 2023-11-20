package ba.com.zira.sdr.songplaylist.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.SongPlaylistService;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylist;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistCreateRequest;
import ba.com.zira.sdr.api.model.songplaylist.SongPlaylistUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Song Playlist", description = "Song playlist rest service")
@RestController
@RequestMapping(value = "song-playlist")
@AllArgsConstructor
public class SongPlaylistRestService {
    private SongPlaylistService songPlaylistService;

    @Operation(summary = "Find song playlist records based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<SongPlaylist> get(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return songPlaylistService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create song playlist")
    @PostMapping
    public PayloadResponse<SongPlaylist> create(@RequestBody final SongPlaylistCreateRequest songPlaylist) throws ApiException {
        return songPlaylistService.create(new EntityRequest<>(songPlaylist));
    }

    @Operation(summary = "Update song playlist")
    @PutMapping(value = "{id}")
    public PayloadResponse<SongPlaylist> edit(@Parameter(required = true, description = "ID of the sample") @PathVariable final Long id,
            @RequestBody final SongPlaylistUpdateRequest songPlaylist) throws ApiException {
        if (songPlaylist != null) {
            songPlaylist.setId(id);
        }
        return songPlaylistService.update(new EntityRequest<>(songPlaylist));
    }

    @Operation(summary = "Delete song playlist")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the record") @PathVariable final Long id)
            throws ApiException {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return songPlaylistService.delete(entityRequest);
    }

    @Operation(summary = "Delete song playlist")
    @DeleteMapping(value = "/delete")
    public PayloadResponse<String> deleteSongFromPlaylist(@RequestParam Long playlistId, @RequestParam Long songId) throws ApiException {
        SongPlaylistCreateRequest req = new SongPlaylistCreateRequest(playlistId, songId);
        return songPlaylistService.deleteByPlaylistIdAndSongId(new EntityRequest<>(req));
    }

}
