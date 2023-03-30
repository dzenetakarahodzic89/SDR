package ba.com.zira.sdr.playlistga.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.PlaylistGAService;
import ba.com.zira.sdr.api.model.playlistga.GAHistoryResponse;
import ba.com.zira.sdr.api.model.playlistga.PlaylistRequestGA;
import ba.com.zira.sdr.api.model.playlistga.PlaylistResponseGA;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "playlistga", description = "Playlist by Genetic algorithm API")
@RestController
@RequestMapping(value = "playlist-ga")
@AllArgsConstructor
public class PlaylistGAServiceRest {

    @Autowired
    PlaylistGAService playlistGeneratorGA;

    @Operation(summary = "Generate playlist by genetic algorithm")
    @PostMapping
    public PagedPayloadResponse<PlaylistResponseGA> generatePlaylistGA(@RequestBody final PlaylistRequestGA request) throws ApiException {
        return playlistGeneratorGA.generatePlaylist(new EntityRequest<>(request));
    }

    @Operation(summary = "Search history of GA results by specific criteria")
    @GetMapping(value = "/history")
    public PagedPayloadResponse<GAHistoryResponse> getHistory(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return playlistGeneratorGA.getHistory(new FilterRequest(filterCriteria, queryCriteria));
    }
}
