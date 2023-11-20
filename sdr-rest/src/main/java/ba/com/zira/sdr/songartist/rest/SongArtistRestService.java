package ba.com.zira.sdr.songartist.rest;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Song artist", description = "Song artist rest service")
@RestController
@RequestMapping(value = "song-artist")
@AllArgsConstructor
public class SongArtistRestService {
    private SongArtistService songArtistService;

    @Operation(summary = "Find song-artist records based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<SongArtistResponse> get(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return songArtistService.get(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create song artist")
    @PostMapping
    public PayloadResponse<SongArtistResponse> create(@RequestBody final SongArtistCreateRequest songArtist) throws ApiException {
        return songArtistService.create(new EntityRequest<>(songArtist));
    }

    @Operation(summary = "Delete song artist")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the record") @PathVariable final Long id) {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return songArtistService.delete(entityRequest);
    }

}
