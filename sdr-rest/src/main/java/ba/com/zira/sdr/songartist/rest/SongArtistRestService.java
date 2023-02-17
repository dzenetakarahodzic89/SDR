package ba.com.zira.sdr.songartist.rest;

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
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.api.model.songartist.SongArtistUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public PagedPayloadResponse<SongArtistResponse> get(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return songArtistService.get(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create song artist")
    @PostMapping()
    public PayloadResponse<SongArtistResponse> create(@RequestBody final SongArtistCreateRequest songArtist) throws ApiException {
        return songArtistService.create(new EntityRequest<>(songArtist));
    }

    @Operation(summary = "Update song artist")
    @PutMapping(value = "{id}")
    public PayloadResponse<SongArtistResponse> edit(
            @Parameter(required = true, description = "ID of the record") @PathVariable final Long id,
            @RequestBody final SongArtistUpdateRequest songArtistUpdateRequest) throws ApiException {
        if (songArtistUpdateRequest != null) {
            songArtistUpdateRequest.setId(id);
        }
        return songArtistService.update(new EntityRequest<>(songArtistUpdateRequest));
    }

    @Operation(summary = "Delete song artist")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<SongArtistResponse> delete(
            @Parameter(required = true, description = "ID of the record") @PathVariable final Long id) throws ApiException {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return songArtistService.delete(entityRequest);
    }

}
