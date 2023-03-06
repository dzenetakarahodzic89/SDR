package ba.com.zira.sdr.connectedmediadetail.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.ConnectedMediaDetailService;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetail;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "connected-media-detail", description = "Connected Media Detail API")
@RestController
@RequestMapping(value = "connected-media-detail")
@AllArgsConstructor
public class ConnectedMediaDetailRestService {

    private ConnectedMediaDetailService connectedMediaDetailService;

    @Operation(summary = "Add a connected media")
    @PostMapping
    public PayloadResponse<ConnectedMediaDetail> create(@RequestBody final ConnectedMediaDetailCreateRequest connectedMediaDetail)
            throws ApiException {
        return connectedMediaDetailService.create(new EntityRequest<>(connectedMediaDetail));
    }

}
