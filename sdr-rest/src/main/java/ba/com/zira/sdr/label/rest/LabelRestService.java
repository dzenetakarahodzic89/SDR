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
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.LabelService;
import ba.com.zira.sdr.api.model.label.LabelCreateRequest;
import ba.com.zira.sdr.api.model.label.LabelResponse;
import ba.com.zira.sdr.api.model.label.LabelUpdateRequest;
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

    @Operation(summary = "Find labels base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<LabelResponse> findAll(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return labelService.findAll(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Find label base on id")
    @GetMapping(value = "{id}")
    public PayloadResponse<LabelResponse> findById(@Parameter(required = true, description = "ID of the label") @PathVariable final Long id)
            throws ApiException {

        return labelService.findById(new EntityRequest<>(id));
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

    @Operation(summary = "Change label status")
    @PutMapping(value = "{id}/changeStatus")
    public PayloadResponse<LabelResponse> changeStatus(
            @Parameter(required = true, description = "ID of the label") @PathVariable final Long id) throws ApiException {
        return labelService.changeStatus(new EntityRequest<>(id));
    }

    /*
     * @Operation(summary = "Delete label")
     *
     * @DeleteMapping(value = "{id}/delete") public PayloadResponse<Label>
     * delete(@Parameter(required = true, description =
     * "ID of the label") @PathVariable final Long id) throws ApiException {
     * return labelService.delete(new EntityRequest<>(id)); }
     */

    @Operation(summary = "Delete label")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<LabelResponse> delete(@Parameter(required = true, description = "ID of the label") @PathVariable final Long id)
            throws ApiException {

        return labelService.delete(new EntityRequest<>(id));
    }

}
