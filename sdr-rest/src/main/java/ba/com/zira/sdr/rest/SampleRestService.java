package ba.com.zira.sdr.rest;

import java.util.Map;

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
import ba.com.zira.sdr.api.SampleService;
import ba.com.zira.sdr.api.model.SampleModel;
import ba.com.zira.sdr.api.model.SampleModelCreateRequest;
import ba.com.zira.sdr.api.model.SampleModelUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "sample", description = "Sample API")
@RestController
@RequestMapping(value = "sample")
@AllArgsConstructor
public class SampleRestService {

    private SampleService sampleService;

    @Operation(summary = "Find Samples base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<SampleModel> find(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return sampleService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create sample")
    @PostMapping
    public PayloadResponse<SampleModel> create(@RequestBody final SampleModelCreateRequest sample) throws ApiException {
        return sampleService.create(new EntityRequest<>(sample));
    }

    @Operation(summary = "Update Sample")
    @PutMapping(value = "{id}")
    public PayloadResponse<SampleModel> edit(@Parameter(required = true, description = "ID of the sample") @PathVariable final Long id,
            @RequestBody final SampleModelUpdateRequest sample) throws ApiException {
        if (sample != null) {
            sample.setId(id);
        }
        return sampleService.update(new EntityRequest<>(sample));
    }

    @Operation(summary = "Activate sample")
    @PutMapping(value = "{id}/activate")
    public PayloadResponse<SampleModel> activate(@Parameter(required = true, description = "ID of the sample") @PathVariable final Long id)
            throws ApiException {
        return sampleService.activate(new EntityRequest<>(id));
    }
}
