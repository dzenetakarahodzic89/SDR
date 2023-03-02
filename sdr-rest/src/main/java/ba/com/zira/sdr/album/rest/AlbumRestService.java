package ba.com.zira.sdr.album.rest;

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
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.AlbumService;
import ba.com.zira.sdr.api.model.album.AlbumCreateRequest;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.album.AlbumSongResponse;
import ba.com.zira.sdr.api.model.album.AlbumUpdateRequest;
import ba.com.zira.sdr.api.model.album.SongOfAlbumUpdateRequest;
import ba.com.zira.sdr.api.model.song.Song;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "album", description = "Album API")
@RestController
@RequestMapping(value = "album")
@AllArgsConstructor
public class AlbumRestService {

    @Autowired
    private AlbumService albumService;

    @Operation(summary = "Create album")
    @PostMapping
    public PayloadResponse<AlbumResponse> create(@RequestBody final AlbumCreateRequest sample) throws ApiException {
        return albumService.create(new EntityRequest<>(sample));
    }

    @Operation(summary = "Update album")
    @PutMapping(value = "{id}")
    public PayloadResponse<AlbumResponse> update(@Parameter(required = true, description = "ID of the album") @PathVariable final Long id,
            @RequestBody final AlbumUpdateRequest album) throws ApiException {
        if (album != null) {
            album.setId(id);
        }
        return albumService.update(new EntityRequest<>(album));
    }

    @Operation(summary = "Delete album")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the album") @PathVariable final Long id)
            throws ApiException {
        return albumService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "Search albums")
    @GetMapping
    public PagedPayloadResponse<AlbumResponse> search(final @RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return albumService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Get all songs from album")
    @GetMapping(value = "{id}/songs")
    public PayloadResponse<AlbumSongResponse> findAllSongsForAlbum(
            @Parameter(required = true, description = "ID of the album") @PathVariable final Long id) throws ApiException {
        return albumService.findAllSongsForAlbum(new EntityRequest<>(id));
    }

    @Operation(summary = "Get album overview")
    @GetMapping(value = "{id}")
    public PayloadResponse<AlbumResponse> getAlbumOverview(
            @Parameter(required = true, description = "ID of the album") @PathVariable final Long id) throws ApiException {
        return albumService.getById(new EntityRequest<>(id));
    }

    @Operation(summary = "Add a new song to the album")
    @PutMapping(value = "/add-song")
    public PayloadResponse<Song> addSongToAlbum(@RequestBody final SongOfAlbumUpdateRequest request) throws ApiException {

        return albumService.addSongToAlbum(new EntityRequest<>(request));
    }

}
