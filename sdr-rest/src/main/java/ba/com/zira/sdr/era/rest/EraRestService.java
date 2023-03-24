package ba.com.zira.sdr.era.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.EraService;
import ba.com.zira.sdr.api.model.era.EraCreateRequest;
import ba.com.zira.sdr.api.model.era.EraResponse;
import ba.com.zira.sdr.api.model.era.EraSearchRequest;
import ba.com.zira.sdr.api.model.era.EraSearchResponse;
import ba.com.zira.sdr.api.model.era.EraUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "era")
@RestController
@RequestMapping(value = "era")
public class EraRestService {

    @Autowired
    EraService eraService;

    @Operation(summary = "Get all era - LoV")
    @GetMapping(value = "/lov")
    public ListPayloadResponse<LoV> getErasLoV() throws ApiException {
        var req = new EmptyRequest();
        return eraService.getEraLoVs(req);
    }

    @Operation(summary = "Era search")
    @PostMapping(value = "/search")
    public ListPayloadResponse<EraSearchResponse> find(@RequestBody final EraSearchRequest request) throws ApiException {
        return eraService.find(new EntityRequest<>(request));
    }

    @Operation(summary = "Create era")
    @PostMapping
    public PayloadResponse<EraResponse> create(@RequestBody final EraCreateRequest era) throws ApiException {
        return eraService.create(new EntityRequest<>(era));
    }

    @Operation(summary = "Update era")
    @PutMapping(value = "{id}")
    public PayloadResponse<EraResponse> update(@Parameter(required = true, description = "ID of the era") @PathVariable final Long id,
            @RequestBody final EraUpdateRequest era) throws ApiException {
        if (era != null) {
            era.setId(id);
        }
        return eraService.update(new EntityRequest<>(era));
    }

    @Operation(summary = "Find era by id")
    @GetMapping(value = "{id}")
    public PayloadResponse<EraResponse> findById(@Parameter(required = true, description = "ID of the era") @PathVariable final Long id)
            throws ApiException {
        return eraService.findById(new EntityRequest<>(id));
    }

}
