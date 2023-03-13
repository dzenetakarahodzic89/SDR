package ba.com.zira.sdr.artist.rest;

import java.util.Map;
import java.util.Optional;

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
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.ArtistByEras;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistSingleResponse;
import ba.com.zira.sdr.api.artist.ArtistSearchRequest;
import ba.com.zira.sdr.api.artist.ArtistSearchResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "artist")
@RestController
@RequestMapping(value = "artist")
public class ArtistRestService {

    @Autowired
    private ArtistService artistService;

    @Operation(summary = "Find artist")
    @GetMapping
    public PagedPayloadResponse<ArtistResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return artistService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find artist by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<ArtistSingleResponse> findById(
            @Parameter(required = true, description = "Id of the artist") @PathVariable final Long id) throws ApiException {
        return artistService.findById(new EntityRequest<>(id));
    }

    @Operation(summary = "Create artist")
    @PostMapping
    public PayloadResponse<ArtistResponse> create(@RequestBody final ArtistCreateRequest request) throws ApiException {
        return artistService.create(new EntityRequest<>(request));
    }

    @Operation(summary = "Create artist from person")
    @PostMapping(value = "create-from-person/{id}")
    public PayloadResponse<ArtistResponse> createFromPerson(
            @Parameter(required = true, description = "Id of the person") @PathVariable final Long id) throws ApiException {
        return artistService.createFromPerson(new EntityRequest<>(id));
    }

    @Operation(summary = "Delete artist")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "Id of the artist") @PathVariable final Long id)
            throws ApiException {
        return artistService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "Update Artist")
    @PutMapping(value = "{id}")
    public PayloadResponse<ArtistResponse> edit(@Parameter(required = true, description = "ID of the artist") @PathVariable final Long id,
            @RequestBody final ArtistUpdateRequest request) throws ApiException {
        if (request != null) {
            request.setId(id);
        }
        return artistService.update(new EntityRequest<>(request));

    }

    @Operation(summary = "Artists by eras")
    @GetMapping("/group-solo-comparison")
    public ListPayloadResponse<ArtistByEras> getArtistsByEras(@RequestParam Long eraId) throws ApiException {
        var req = new EntityRequest<>(eraId);
        return artistService.getArtistsByEras(req);
    }

    @Operation(summary = "Artists by eras count")
    @GetMapping("/group-solo-count")
    public PayloadResponse<ArtistByEras> getArtistsByErasCount(@RequestParam Long era) throws ApiException {
        var req = new EntityRequest<>(era);
        return artistService.countArtistsByEras(req);
    }

    @Operation(summary = "All artist ids and names")
    @GetMapping("/lov")
    public ListPayloadResponse<LoV> getArtistNames() throws ApiException {
        var req = new EmptyRequest();
        return artistService.getArtistNames(req);
    }

    @Operation(summary = "Get artists by search")
    @GetMapping("search")
    public ListPayloadResponse<ArtistSearchResponse> getArtistsBySearch(@RequestParam("name") Optional<String> name,
            @RequestParam("album") Optional<Long> album, @RequestParam("genre") Optional<Long> genre,
            @RequestParam("isSolo") Optional<Boolean> isSolo, @RequestParam("sortBy") Optional<String> sortBy) throws ApiException {
        var newSearchRequest = new ArtistSearchRequest(name.orElse(""), album.orElse(null), genre.orElse(null), isSolo.orElse(true),
                sortBy.orElse(""));
        return artistService.getArtistsBySearch(new EntityRequest<>(newSearchRequest));
    }

    @Operation(summary = "10 random artists for default search page")
    @GetMapping("/random-for-search")
    public ListPayloadResponse<ArtistSearchResponse> getRandomArtistsForSearch() throws ApiException {
        var req = new EmptyRequest();
        return artistService.getRandomArtistsForSearch(req);
    }

}
