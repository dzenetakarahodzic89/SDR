package ba.com.zira.sdr.artist.rest;

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
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
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

    @Operation(summary = "Find artist")
    @GetMapping
    public PagedPayloadResponse<ArtistResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return artistService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create artist")
    @PostMapping
    public PayloadResponse<ArtistResponse> create(@RequestBody final ArtistCreateRequest request) throws ApiException {
        return artistService.create(new EntityRequest<>(request));
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

}
