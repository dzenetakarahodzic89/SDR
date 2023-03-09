package ba.com.zira.sdr.song.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import ba.com.zira.sdr.api.SongService;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.Song;
import ba.com.zira.sdr.api.model.song.SongCreateRequest;
import ba.com.zira.sdr.api.model.song.SongSearchRequest;
import ba.com.zira.sdr.api.model.song.SongSearchResponse;
import ba.com.zira.sdr.api.model.song.SongSingleResponse;
import ba.com.zira.sdr.api.model.song.SongUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "song", description = "Song API")
@RestController
@RequestMapping(value = "song")
@AllArgsConstructor
public class SongRestService {

    @Autowired
    private SongService songService;

    @Operation(summary = "Create song")
    @PostMapping
    public PayloadResponse<Song> create(@RequestBody final SongCreateRequest song) throws ApiException {
        return songService.create(new EntityRequest<>(song));
    }

    @Operation(summary = "Find songs based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<Song> retrieveAll(@RequestParam final Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return songService.retrieveAll(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find song by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<SongSingleResponse> retrieveById(
            @Parameter(required = true, description = "ID of the song") @PathVariable final Long id) throws ApiException {
        return songService.retrieveById(new EntityRequest<>(id));
    }

    @Operation(summary = "Get song ids and names which are not tied to the album specified by parameter albumId")
    @GetMapping(value = "/not-in-album/{albumId}")
    public ListPayloadResponse<LoV> findLabelsNotInAlbum(
            @Parameter(required = true, description = "Id of the album") @PathVariable final Long albumId) throws ApiException {
        return songService.retrieveNotInAlbum(new EntityRequest<>(albumId));
    }

    @Operation(summary = "Update song")
    @PutMapping(value = "{id}")
    public PayloadResponse<Song> update(@Parameter(required = true, description = "ID of the song") @PathVariable final Long id,
            @RequestBody final SongUpdateRequest song) throws ApiException {
        if (song != null) {
            song.setId(id);
        }
        return songService.update(new EntityRequest<>(song));
    }

    @Operation(summary = "Delete song")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the song") @PathVariable final Long id)
            throws ApiException {
        return songService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "Song search")
    @PostMapping(value = "/search")
    public ListPayloadResponse<SongSearchResponse> find(@RequestBody final SongSearchRequest request) throws ApiException {
        return songService.find(new EntityRequest<>(request));
    }

}
