package ba.com.zira.sdr.instrument.rest;

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
import ba.com.zira.sdr.api.InstrumentService;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Instrument", description = "Instrument rest service")
@RestController
@RequestMapping(value = "instrument")
@AllArgsConstructor
public class InstrumentRestService {
    private InstrumentService instrumentService;

    @Operation(summary = "Find instrument records based on filter criteria")
    @GetMapping()
    public PagedPayloadResponse<InstrumentResponse> get(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return instrumentService.get(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create instrument")
    @PostMapping
    public PayloadResponse<InstrumentResponse> create(@RequestBody final InstrumentCreateRequest instrument) throws ApiException {
        return instrumentService.create(new EntityRequest<>(instrument));
    }

    @Operation(summary = "Delete instrument")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<InstrumentResponse> delete(
            @Parameter(required = true, description = "ID of the record") @PathVariable final Long id) throws ApiException {
        EntityRequest<Long> entityRequest = new EntityRequest<>();
        entityRequest.setEntity(id);
        return instrumentService.delete(entityRequest);
    }

    @Operation(summary = "Update instrument")
    @PutMapping(value = "{id}")
    public PayloadResponse<InstrumentResponse> edit(
            @Parameter(required = true, description = "ID of the record") @PathVariable final Long id,
            @RequestBody final InstrumentUpdateRequest instrumentUpdateRequest) throws ApiException {
        if (instrumentUpdateRequest != null) {
            instrumentUpdateRequest.setId(id);
        }
        return instrumentService.update(new EntityRequest<>(instrumentUpdateRequest));
    }

    @Operation(summary = "Activate Instrument")
    @PutMapping(value = "{id}/activate")
    public PayloadResponse<InstrumentResponse> activate(
            @Parameter(required = true, description = "ID of the sample") @PathVariable final Long id) throws ApiException {
        return instrumentService.activate(new EntityRequest<>(id));
    }

}
