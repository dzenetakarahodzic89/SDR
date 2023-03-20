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
import ba.com.zira.commons.message.request.ListRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.InstrumentService;
import ba.com.zira.sdr.api.instrument.InsertSongInstrumentRequest;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentSearchRequest;
import ba.com.zira.sdr.api.instrument.InstrumentSearchResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.api.instrument.ResponseSongInstrument;
import ba.com.zira.sdr.api.instrument.ResponseSongInstrumentEra;
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

    @Operation(summary = "Return instrument with specified id")
    @GetMapping(value = "{id}")
    public PayloadResponse<InstrumentResponse> get(
            @Parameter(required = true, description = "ID of the record") @PathVariable final Long id) throws ApiException {
        EntityRequest<Long> request = new EntityRequest<>();
        request.setEntity(id);
        return instrumentService.get(request);
    }

    @Operation(summary = "Find instrument records based on filter criteria")
    @GetMapping(value = "filter")
    public PagedPayloadResponse<InstrumentResponse> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return instrumentService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Instrument Search")
    @GetMapping(value = "search")
    public PagedPayloadResponse<InstrumentSearchResponse> search(
            @Parameter(required = false, description = "Name of the instrument") @RequestParam(required = false) final String name,
            @Parameter(required = false, description = "Sorting method") @RequestParam(required = false) final String sortBy)
            throws ApiException {
        return instrumentService.search(new EntityRequest<>(new InstrumentSearchRequest(name, sortBy)));
    }

    @Operation(summary = "Create instrument")
    @PostMapping
    public PayloadResponse<InstrumentResponse> create(@RequestBody final InstrumentCreateRequest instrument) throws ApiException {
        return instrumentService.create(new EntityRequest<>(instrument));
    }

    @Operation(summary = "Delete instrument")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<String> delete(@Parameter(required = true, description = "ID of the record") @PathVariable final Long id) {
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

    @Operation(summary = "Insert instruments for song")
    @PostMapping(value = "instrument-to-song")
    public ListPayloadResponse<ResponseSongInstrument> insertInstrumentToSong(
            @RequestBody final ListRequest<InsertSongInstrumentRequest> request) throws ApiException {
        return instrumentService.insertInstrumentsToSong(request);
    }

    @Operation(summary = "Get number of songs of instruments by era")
    @GetMapping(value = "/{id}/era-timeline")
    public ListPayloadResponse<ResponseSongInstrumentEra> getInstrumentSongByEra(
            @Parameter(required = true, description = "ID of the instrument") @PathVariable final Long id) throws ApiException {
        return instrumentService.findAllSongsInErasForInstruments(new EntityRequest<>(id));

    }
}
