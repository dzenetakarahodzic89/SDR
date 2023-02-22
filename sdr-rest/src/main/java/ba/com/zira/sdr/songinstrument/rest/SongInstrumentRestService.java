package ba.com.zira.sdr.songinstrument.rest;


import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import ba.com.zira.sdr.api.SongInstrumentService;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrument;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentCreateRequest;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "songInstrument", description = "SongInstrument API")
@RestController
@RequestMapping(value = "songInstrument")
@AllArgsConstructor
public class SongInstrumentRestService {

    private SongInstrumentService songInstrumentService;

    @Operation(summary = "Find SongInstrument base on filter criteria")
    @GetMapping
    public PagedPayloadResponse<SongInstrument> find(@RequestParam Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria)
            throws ApiException {
        return songInstrumentService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Create songInstrument")
    @PostMapping
    public PayloadResponse<SongInstrument> create(@RequestBody final SongInstrumentCreateRequest songInstrument) throws ApiException {
        return songInstrumentService.create(new EntityRequest<>(songInstrument));
    }


    
    @Operation(summary = "Update SongInstrument")
    @PutMapping(value = "{id}")
    public PayloadResponse<SongInstrument> edit(@Parameter(required = true, description = "ID of the SongInstrument") @PathVariable final Long id,
            @RequestBody final SongInstrumentUpdateRequest songInstrument) throws ApiException {
        if (songInstrument != null) {
            songInstrument.setId(id);
        }
        return songInstrumentService.update(new EntityRequest<>(songInstrument));
    }

    
    
    @Operation(summary = "Delete comment")
    @DeleteMapping(value = "{id}")
    public PayloadResponse<SongInstrument> delete(@Parameter(required = true, description = "ID of the sample") @PathVariable final Long id) throws ApiException {
        return songInstrumentService.delete(new EntityRequest<>(id));
    }

  
 
}