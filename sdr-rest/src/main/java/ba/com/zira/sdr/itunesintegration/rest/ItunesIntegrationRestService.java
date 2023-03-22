package ba.com.zira.sdr.itunesintegration.rest;


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
import ba.com.zira.sdr.api.ItunesIntegrationService;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationResponse;
import ba.com.zira.sdr.api.model.itunesintegration.ItunesIntegrationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "ItunesIntegration", description = "ItunesIntegration API")
@RestController
@RequestMapping(value = "itunesintegration")
@AllArgsConstructor
public class ItunesIntegrationRestService {
    private ItunesIntegrationService itunesintegrationService; 

    @Operation(summary = "Find itunesintegration base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<ItunesIntegrationResponse> get(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return itunesintegrationService.get(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create itunesintegration")
    @PostMapping
    public PayloadResponse<ItunesIntegrationResponse> create(@RequestBody final ItunesIntegrationCreateRequest request) throws ApiException {
        return itunesintegrationService.create(new EntityRequest<>(request));
    }

    @Operation(summary = "Update itunesintegration")
    @PutMapping(value = "{id}")
    public PayloadResponse<ItunesIntegrationResponse> update(
            @Parameter(required = true, description = "ID of the itunesintegration") @PathVariable final Long id,
            @RequestBody final ItunesIntegrationUpdateRequest request) throws ApiException {
        if (request != null) {
            request.setId(id);
        }
        return itunesintegrationService.update(new EntityRequest<>(request));
    }

    @Operation(summary = "Delete itunesintegration")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the itunesintegration") @PathVariable final Long id)
            throws ApiException {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return itunesintegrationService.delete(entityRequest);
    }

  
}
