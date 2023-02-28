package ba.com.zira.sdr.era.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.ChordProgressionService;
import ba.com.zira.sdr.api.EraService;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionByEraResponse;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
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

}
