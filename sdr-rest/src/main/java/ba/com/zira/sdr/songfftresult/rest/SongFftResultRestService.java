package ba.com.zira.sdr.songfftresult.rest;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.SongFftResultService;
import ba.com.zira.sdr.api.model.song.fft.SongFftResult;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "songfftresult", description = "SongFftResult API")
@RestController
@RequestMapping(value = "songfftresult")
@AllArgsConstructor
public class SongFftResultRestService {

    private SongFftResultService songFftResultService;

    @Operation(summary = "Find Samples base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<SongFftResult> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return songFftResultService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create sample")
    @PostMapping
    public PayloadResponse<SongFftResult> create(@RequestBody final SongFftResultCreateRequest sample) throws ApiException {
        return songFftResultService.create(new EntityRequest<>(sample));
    }

    @Operation(summary = "Update sample")
    @PutMapping(value = "{id}")
    public PayloadResponse<SongFftResult> edit(@Parameter(required = true, description = "ID of the sample") @PathVariable final Long id,
            @RequestBody final SongFftResultUpdateRequest sample) throws ApiException {
        if (sample != null) {
            sample.setId(id);
        }
        return songFftResultService.update(new EntityRequest<>(sample));
    }

    @Operation(summary = "Activate sample")
    @PutMapping(value = "{id}/activate")
    public PayloadResponse<SongFftResult> activate(
            @Parameter(required = true, description = "ID of the sample") @PathVariable final Long id) throws ApiException {
        return songFftResultService.activate(new EntityRequest<>(id));
    }

    @Operation(summary = "Delete sample")
    @DeleteMapping(value = "{id}/delete")
    public PayloadResponse<SongFftResult> delete(@Parameter(required = true, description = "ID of the sample") @PathVariable final Long id)
            throws ApiException {
        return songFftResultService.delete(new EntityRequest<>(id));
    }
}