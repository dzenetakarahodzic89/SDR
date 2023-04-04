package ba.com.zira.sdr.playlist.rest;

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
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.PlaylistService;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistOfUserResponse;
import ba.com.zira.sdr.api.model.playlist.PlaylistResponse;
import ba.com.zira.sdr.api.model.playlist.PlaylistSearchRequest;
import ba.com.zira.sdr.api.model.playlist.PlaylistUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "playlist", description = "Playlist API")
@RestController
@RequestMapping(value = "playlist")
@AllArgsConstructor
public class PlaylistRestService {

    private PlaylistService playlistService;

    @Operation(summary = "Find Playlists base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<Playlist> find(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return playlistService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Get playlist")
    @GetMapping(value = "/get/{id}")
    public ListPayloadResponse<PlaylistResponse> get(
            @Parameter(required = true, description = "Id of the playlist") @PathVariable final Long id) throws ApiException {
        return playlistService.getAll(new EntityRequest<>(id));
    }

    @Operation(summary = "Get playlist")
    @GetMapping(value = "/get-playlist/{id}")
    public ListPayloadResponse<PlaylistResponse> getAll(
            @Parameter(required = true, description = "Id of the playlist") @PathVariable final Long id) throws ApiException {
        return playlistService.getAllPlaylistInfo(new EntityRequest<>(id));
    }

    @Operation(summary = "Find Playlists based on custom filter")
    @GetMapping(value = "search")
    public PagedPayloadResponse<Playlist> findByNameSongGenre(
            @Parameter(required = false, description = "Name of the playlist") @RequestParam(required = false) final String name,
            @Parameter(required = false, description = "Id of a song in the playlist") @RequestParam(required = false) final Long songId,
            @Parameter(required = false, description = "Id of a genre in the playlist") @RequestParam(required = false) final Long genreId,
            @Parameter(required = false, description = "Sorting method") @RequestParam(required = false) final String sortBy)
            throws ApiException {
        return playlistService
                .searchByNameSongGenre(new EntityRequest<PlaylistSearchRequest>(new PlaylistSearchRequest(name, songId, genreId, sortBy)));

    }

    @Operation(summary = "Create playlist")
    @PostMapping
    public PayloadResponse<Playlist> create(@RequestBody final PlaylistCreateRequest playlist) throws ApiException {
        return playlistService.create(new EntityRequest<>(playlist));
    }

    @Operation(summary = "Update Playlist")
    @PutMapping(value = "{id}")
    public PayloadResponse<Playlist> edit(@Parameter(required = true, description = "ID of the playlist") @PathVariable final Long id,
            @RequestBody final PlaylistUpdateRequest playlist) throws ApiException {
        if (playlist != null) {
            playlist.setId(id);
        }
        return playlistService.update(new EntityRequest<>(playlist));
    }

    @Operation(summary = "Delete playlist")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the playlist") @PathVariable final Long id)
            throws ApiException {
        return playlistService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "Get random playlist of a user")
    @GetMapping(value = "{userCode}")
    public ListPayloadResponse<PlaylistOfUserResponse> findPlaylistOfUser(
            @Parameter(required = true, description = "User code") @PathVariable final String userCode) throws ApiException {
        return playlistService.findPlaylistOfUser(new EntityRequest<>(userCode));

    }

}
