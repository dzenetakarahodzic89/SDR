package ba.com.zira.sdr.moritsintegration.rest;

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
import ba.com.zira.sdr.api.MoritsIntegrationService;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegration;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MoritsIntegrationUpdateRequest;
import ba.com.zira.sdr.api.model.moritsintegration.MusicMatchIntegrationStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "moritsintegration", description = "Morits Lyrics Integration API")
@RestController
@RequestMapping(value = "morits-integration")
@AllArgsConstructor
public class MoritsIntegrationRestService {

    private MoritsIntegrationService moritsIntegrationService;

    @Operation(summary = "Find morits lyric integration base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<MoritsIntegration> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return moritsIntegrationService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create morits lyric integration")
    @PostMapping
    public PayloadResponse<MoritsIntegration> create(@RequestBody final MoritsIntegrationCreateRequest moritsIntegration)
            throws ApiException {
        return moritsIntegrationService.create(new EntityRequest<>(moritsIntegration));
    }

    @Operation(summary = "Update morits lyric integration")
    @PutMapping(value = "{id}")
    public PayloadResponse<MoritsIntegration> update(
            @Parameter(required = true, description = "ID of the morits lyric integration") @PathVariable final Long id,
            @RequestBody final MoritsIntegrationUpdateRequest moritsIntegration) throws ApiException {
        if (moritsIntegration != null) {
            moritsIntegration.setId(id);
        }
        return moritsIntegrationService.update(new EntityRequest<>(moritsIntegration));
    }

    @Operation(summary = "Delete morits lyric integration")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<String> delete(
            @Parameter(required = true, description = "ID of the morits lyric integration") @PathVariable final Long id)
            throws ApiException {
        return moritsIntegrationService.delete(new EntityRequest<>(id));
    }

    @Operation(summary = "Get Music Match statistics")
    @GetMapping(value = "statistics")
    public PayloadResponse<MusicMatchIntegrationStatistics> getMusicMatchStatistics() throws ApiException {
        EmptyRequest request = new EmptyRequest();
        return moritsIntegrationService.getMusicMatchStatistics(request);
    }
}
