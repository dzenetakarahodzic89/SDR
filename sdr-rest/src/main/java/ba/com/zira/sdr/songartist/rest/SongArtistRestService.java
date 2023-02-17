package ba.com.zira.sdr.songartist.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Song artist", description = "Song artist rest service")
@RestController
@RequestMapping(value = "song-artist")
@AllArgsConstructor
public class SongArtistRestService {
    private SongArtistService songArtistService;

    @Operation(summary = "Find song-artist records based on filter criteria")
    @GetMapping()
    public PagedPayloadResponse<SongArtistResponse> getFiltered(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return songArtistService.getFiltered(new FilterRequest(filterCriteria, queryCriteria));
    }
}
