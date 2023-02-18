package ba.com.zira.sdr.artist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistDeleteRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "artist")
@RestController
@RequestMapping(value = "artist")
public class ArtistRestService {

    @Autowired
    private ArtistService artistService;

    @Operation(summary = "Get all")
    @GetMapping(value = "all")
    public ListPayloadResponse<ArtistResponse> getAll() throws ApiException {
        var request = new EmptyRequest();
        return artistService.getAll(request);
    }

    @Operation(summary = "Create artist")
    @PostMapping
    public PayloadResponse<ArtistResponse> create(@RequestBody final ArtistCreateRequest request) throws ApiException {
        return artistService.create(new EntityRequest<>(request));
    }

    @Operation(summary = "Delete Artist")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<ArtistResponse> delete(@PathVariable final Long id) throws ApiException {
        ArtistDeleteRequest request = new ArtistDeleteRequest();
        request.setId(id);
        return artistService.delete(new EntityRequest<>(request));
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

}
