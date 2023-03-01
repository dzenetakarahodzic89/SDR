package ba.com.zira.sdr.multisearchhistory.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.MultiSearchHistoryService;
import ba.com.zira.sdr.api.model.multisearchhistory.MultiSearchHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "multisearch")
@RestController
@RequestMapping(value = "multisearchhistory")
public class MultiSearchHistoryRestService {

    @Autowired
    private MultiSearchHistoryService multiSearchHistoryService;

    @Operation(summary = "Create multisearch history")
    @PostMapping(value = "create")
    public PayloadResponse<String> create() throws ApiException {
        var req = new EmptyRequest();
        return multiSearchHistoryService.create(req);
    }

    @Operation(summary = "Get last multisearch history")
    @GetMapping(value = "get-last")
    public PayloadResponse<MultiSearchHistoryResponse> getLast() throws ApiException {
        EmptyRequest request = new EmptyRequest();
        return multiSearchHistoryService.getLast(request);
    }

    @Operation(summary = "Get all history ordered by refresh time")
    @GetMapping(value = "all-by-refresh-time")
    public ListPayloadResponse<MultiSearchHistoryResponse> getAllOrderedByRefreshTime() throws ApiException {
        EmptyRequest request = new EmptyRequest();
        return multiSearchHistoryService.getAllOrderedByRefreshTime(request);
    }

}
