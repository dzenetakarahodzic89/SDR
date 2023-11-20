package ba.com.zira.sdr.spotifyintegration.rest;

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
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.SpotifyIntegrationService;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationStatistics;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "spotifyintegration", description = "Spotify Integration API")
@RestController
@RequestMapping(value = "spotify-integration")
@AllArgsConstructor
public class SpotifyIntegrationRestService {

    private SpotifyIntegrationService spotifyIntegrationService;

    @Operation(summary = "Find spotify integration base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<SpotifyIntegrationResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return spotifyIntegrationService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create spotify integration")
    @PostMapping
    public PayloadResponse<SpotifyIntegrationResponse> create(@RequestBody final SpotifyIntegrationCreateRequest spotifyIntegration)
            throws ApiException {
        return spotifyIntegrationService.create(new EntityRequest<>(spotifyIntegration));
    }

    @Operation(summary = "Update spotify integration")
    @PutMapping(value = "{id}")
    public PayloadResponse<SpotifyIntegrationResponse> update(
            @Parameter(required = true, description = "ID of the spotify integration") @PathVariable final Long id,
            @RequestBody final SpotifyIntegrationUpdateRequest spotifyIntegration) throws ApiException {
        if (spotifyIntegration != null) {
            spotifyIntegration.setId(id);
        }
        return spotifyIntegrationService.update(new EntityRequest<>(spotifyIntegration));
    }

    @Operation(summary = "Delete spotify integration")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<String> delete(
            @Parameter(required = true, description = "ID of the spotify integration") @PathVariable final Long id) throws ApiException {
        return spotifyIntegrationService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "Get data for Spotify statistics")
    @GetMapping(value = "statistics")
    public PayloadResponse<SpotifyIntegrationStatistics> getDataForStatistics() throws ApiException {
        return spotifyIntegrationService.getDataForStatistics(new EmptyRequest());
    }
}
