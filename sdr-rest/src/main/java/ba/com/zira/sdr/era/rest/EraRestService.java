package ba.com.zira.sdr.era.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.EraService;
import ba.com.zira.sdr.api.model.era.EraSearchRequest;
import ba.com.zira.sdr.api.model.era.EraSearchResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
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

}
