package ba.com.zira.sdr.audiodbintegration.rest;

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
import ba.com.zira.sdr.api.AudioDBIntegrationService;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegration;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.audiodb.AudioDBIntegrationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "audioDBIntegration", description = "audioDBIntegration API")
@RestController
@RequestMapping(value = "audioDBIntegration")
@AllArgsConstructor
public class AudioDBIntegrationRestService {

    private AudioDBIntegrationService audioDBIntegrationService;

    @Operation(summary = "Find AudioDB Integration base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<AudioDBIntegration> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return audioDBIntegrationService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create AudioDB Integration")
    @PostMapping
    public PayloadResponse<AudioDBIntegration> create(@RequestBody final AudioDBIntegrationCreateRequest sample) throws ApiException {
        return audioDBIntegrationService.create(new EntityRequest<>(sample));
    }

    @Operation(summary = "Update AudioDB Integration")
    @PutMapping(value = "{id}")
    public PayloadResponse<AudioDBIntegration> edit(
            @Parameter(required = true, description = "ID of the AudioDB Integration") @PathVariable final Long id,
            @RequestBody final AudioDBIntegrationUpdateRequest sample) throws ApiException {
        if (sample != null) {
            sample.setId(id);
        }
        return audioDBIntegrationService.update(new EntityRequest<>(sample));
    }

    @Operation(summary = "Activate AudioDB Integration")
    @PutMapping(value = "{id}/activate")
    public PayloadResponse<AudioDBIntegration> activate(
            @Parameter(required = true, description = "ID of the AudioDB Integration") @PathVariable final Long id) throws ApiException {
        return audioDBIntegrationService.activate(new EntityRequest<>(id));
    }

    @Operation(summary = "Delete sample")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<AudioDBIntegration> delete(
            @Parameter(required = true, description = "ID of the AudioDB Integration") @PathVariable final Long id) throws ApiException {
        return audioDBIntegrationService.delete(new EntityRequest<>(id));
    }
}
