package ba.com.zira.sdr.deezerintegration.rest;

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
import ba.com.zira.sdr.api.DeezerIntegrationService;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "deezerintegration", description = "Deezer Integration API")
@RestController
@RequestMapping(value = "deezer-integration")
@AllArgsConstructor
public class DeezerIntegrationRestService {

    private DeezerIntegrationService deezerIntegrationService;

    @Operation(summary = "Find deezer integration base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<DeezerIntegration> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return deezerIntegrationService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create deezer integration")
    @PostMapping
    public PayloadResponse<DeezerIntegration> create(@RequestBody final DeezerIntegrationCreateRequest deezerIntegration)
            throws ApiException {
        return deezerIntegrationService.create(new EntityRequest<>(deezerIntegration));
    }

    @Operation(summary = "Update deezer integration")
    @PutMapping(value = "{id}")
    public PayloadResponse<DeezerIntegration> edit(
            @Parameter(required = true, description = "ID of the deezer integration") @PathVariable final String id,
            @RequestBody final DeezerIntegrationUpdateRequest deezerIntegration) throws ApiException {
        if (deezerIntegration != null) {
            deezerIntegration.setId(id);
        }
        return deezerIntegrationService.update(new EntityRequest<>(deezerIntegration));
    }

    @Operation(summary = "Delete deezer integration")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<String> delete(
            @Parameter(required = true, description = "ID of the deezer integration") @PathVariable final String id) throws ApiException {
        return deezerIntegrationService.delete(new EntityRequest<>(id));
    }
}
