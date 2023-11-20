package ba.com.zira.sdr.multisearch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.MultiSearchService;
import ba.com.zira.sdr.api.model.multisearch.MultiSearchResponse;
import ba.com.zira.sdr.api.model.wiki.WikiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "multisearch")
@RestController
@RequestMapping(value = "multisearch")
public class MultiSearchRestService {

    @Autowired
    private MultiSearchService multiSearchService;

    @Operation(summary = "Find by name")
    @GetMapping(value = "find-by-name")
    public ListPayloadResponse<MultiSearchResponse> find(@RequestParam String name) throws ApiException {
        EntityRequest<String> request = new EntityRequest<>();
        request.setEntity(name);
        return multiSearchService.find(request);
    }

    @Operation(summary = "Find all types")
    @GetMapping(value = "find-types")
    public ListPayloadResponse<WikiResponse> findAll() throws ApiException {
        var req = new EmptyRequest();
        return multiSearchService.findWiki(req);
    }

    @Operation(summary = "Get all")
    @GetMapping(value = "all")
    public ListPayloadResponse<MultiSearchResponse> getAll() throws ApiException {
        var request = new EmptyRequest();
        return multiSearchService.getAll(request);
    }

    @Operation(summary = "Get random")
    @GetMapping(value = "random")
    public ListPayloadResponse<MultiSearchResponse> getRandom() throws ApiException {
        var request = new EmptyRequest();
        return multiSearchService.getRandom(request);
    }
}
