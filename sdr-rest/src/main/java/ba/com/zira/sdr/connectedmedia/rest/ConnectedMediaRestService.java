package ba.com.zira.sdr.connectedmedia.rest;

import java.util.Map;

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
import ba.com.zira.sdr.api.ConnectedMediaService;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMedia;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaCreateRequest;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "connected-media", description = "Connected Media API")
@RestController
@RequestMapping(value = "connected-media")
@AllArgsConstructor
public class ConnectedMediaRestService {

    private ConnectedMediaService connectedMediaService;

    @Operation(summary = "Find connected media based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<ConnectedMedia> find(@RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return connectedMediaService.find(new FilterRequest(filterCriteria, queryCriteria));
    }

    @Operation(summary = "Add a connected media")
    @PostMapping
    public PayloadResponse<ConnectedMedia> create(@RequestBody final ConnectedMediaCreateRequest connectedMedia) throws ApiException {
        return connectedMediaService.create(new EntityRequest<>(connectedMedia));
    }

    @Operation(summary = "Update a connected media")
    @PutMapping(value = "{id}")
    public PayloadResponse<ConnectedMedia> edit(
            @Parameter(required = true, description = "Id of the connected media") @PathVariable final Long id,
            @RequestBody final ConnectedMediaUpdateRequest connectedMedia) throws ApiException {
        if (connectedMedia != null) {
            connectedMedia.setId(id);
        }
        return connectedMediaService.update(new EntityRequest<>(connectedMedia));
    }

}
