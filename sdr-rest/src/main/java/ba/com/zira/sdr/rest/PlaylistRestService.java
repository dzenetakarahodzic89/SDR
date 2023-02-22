package ba.com.zira.sdr.rest;

import java.util.Map;

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
import ba.com.zira.sdr.api.PlaylistService;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.playlist.PlaylistCreateRequest;
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
    @PutMapping(value = "{id}/delete")
    public PayloadResponse<Playlist> delete(@Parameter(required = true, description = "ID of the playlist") @PathVariable final Long id)
            throws ApiException {
        return playlistService.delete(new EntityRequest<>(id));
    }

}
