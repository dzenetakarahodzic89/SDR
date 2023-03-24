package ba.com.zira.sdr.label.rest;

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
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.LabelService;
import ba.com.zira.sdr.api.model.label.LabelArtistResponse;
import ba.com.zira.sdr.api.model.label.LabelCreateRequest;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.api.model.label.LabelSearchRequest;
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "label", description = "Label API")
@RestController
@RequestMapping(value = "label")
@AllArgsConstructor
public class LabelRestService {

    @Autowired
    private LabelService labelService;

    @Operation(summary = "Find Labels based on custom filter")
    @GetMapping(value = "search")
    public PagedPayloadResponse<LabelResponse> findByNameFounder(
            @Parameter(required = false, description = "Name of the label") @RequestParam(required = false) final String name,
            @Parameter(required = false, description = "Id of a founder of the label") @RequestParam(required = false) final Long founder,
            @Parameter(required = false, description = "Sorting method") @RequestParam(required = false) final String sortBy) {
        return labelService.searchLabelsByNameFounder(new EntityRequest<>(new LabelSearchRequest(name, founder, sortBy)));
    }

    @Operation(summary = "Find labels base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<LabelResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return labelService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find label by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<LabelArtistResponse> findById(
            @Parameter(required = true, description = "ID of the label") @PathVariable final Long id) throws ApiException {
        return labelService.findById(new EntityRequest<>(id));
    }

    @Operation(summary = "Get all label ids and names")
    @GetMapping(value = "/lov")
    public ListPayloadResponse<LoV> findAllLoVs() throws ApiException {
        var request = new EmptyRequest();
        return labelService.retrieveAllLoVs(request);
    }

    @Operation(summary = "Create label")
    @PostMapping
    public PayloadResponse<LabelResponse> create(@RequestBody final LabelCreateRequest label) throws ApiException {
        return labelService.create(new EntityRequest<>(label));
    }

    @Operation(summary = "Update label")
    @PutMapping(value = "{id}")
    public PayloadResponse<LabelResponse> update(@Parameter(required = true, description = "ID of the label") @PathVariable final Long id,
            @RequestBody final LabelUpdateRequest label) throws ApiException {
        if (label != null) {
            label.setId(id);
        }
        return labelService.update(new EntityRequest<>(label));
    }

    @Operation(summary = "Delete label")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the label") @PathVariable final Long id)
            throws ApiException {

        return labelService.delete(new EntityRequest<>(id));
    }

}
