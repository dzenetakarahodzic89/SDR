package ba.com.zira.sdr.chordprogression.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.ChordProgressionService;
import ba.com.zira.sdr.api.model.chordprogression.ChordProgressionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "chordprogression")
@RestController
@RequestMapping(value = "chordprogression")
public class ChordProgressionRestService {
    @Autowired
    ChordProgressionService chordProgressionService;

    @Operation(summary = "Get all")
    @GetMapping(value = "get-all")
    public ListPayloadResponse<ChordProgressionResponse> findAll() throws ApiException {
        var req = new EmptyRequest();
        return chordProgressionService.getAll(req);
    }
}
