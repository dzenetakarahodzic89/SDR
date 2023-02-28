package ba.com.zira.sdr.generateplaylist.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import ba.com.zira.sdr.api.PlaylistGenerateService;
import ba.com.zira.sdr.api.model.generateplaylist.SavePlaylistRequest;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.api.model.song.Song;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Generate playlist", description = "generate playlist service")
@RestController
@RequestMapping(value = "playlist-generate")
@AllArgsConstructor
public class PlaylistGenerateRestService {
    private PlaylistGenerateService playlistGenerateService;

    @Operation(summary = "Generate playlist based on specific criteria")
    @GetMapping
    public PagedPayloadResponse<Song> get(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return playlistGenerateService.generatePlaylist(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Saving playlist that is generated")
    @PostMapping
    public PayloadResponse<Playlist> post(@RequestBody final SavePlaylistRequest savePlaylistRequest) throws ApiException {
        return playlistGenerateService.saveGeneratedPlaylist(new EntityRequest<>(savePlaylistRequest));
    }
}
