package ba.com.zira.sdr.chordprogression.rest;

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
import ba.com.zira.sdr.api.ChordProgressionService;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionCreateRequest;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "chordprogression")
@RestController
@RequestMapping(value = "chordprogression")
public class ChordProgressionRestService {
    @Autowired
    ChordProgressionService chordProgressionService;

    @Operation(summary = "Get all chord progressions")
    @GetMapping
    public PagedPayloadResponse<ChordProgressionResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return chordProgressionService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create chord progression")
    @PostMapping
    public PayloadResponse<ChordProgressionResponse> create(@RequestBody final ChordProgressionCreateRequest sample) throws ApiException {
        return chordProgressionService.create(new EntityRequest<>(sample));
    }

    @Operation(summary = "Update Chord progression")
    @PutMapping(value = "{id}")
    public PayloadResponse<ChordProgressionResponse> update(
            @Parameter(required = true, description = "ID of the chord") @PathVariable final Long id,
            @RequestBody final ChordProgressionUpdateRequest sample) throws ApiException {
        if (sample != null) {
            sample.setId(id);
        }
        return chordProgressionService.update(new EntityRequest<>(sample));
    }

    @Operation(summary = "Delete chord progression")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<ChordProgressionResponse> delete(
            @Parameter(required = true, description = "ID of the chord") @PathVariable final Long id) throws ApiException {
        return chordProgressionService.delete(new EntityRequest<>(id));
    }
}
