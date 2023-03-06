package ba.com.zira.sdr.fileuploadsegment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.FileUploadSegmentService;
import ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentCreateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "fileuploadsegment")
@RestController
@RequestMapping(value = "file-upload-segment")
public class FileUploadSegmentRestService {
    @Autowired
    FileUploadSegmentService fileUploadSegmentService;

    @Operation(summary = "Create fiile upload segment")
    @PostMapping
    public PayloadResponse<String> create(@RequestBody final FileUploadSegmentCreateRequest req) throws ApiException {
        return fileUploadSegmentService.create(new EntityRequest<>(req));
    }

    @Operation(summary = "Check media upload status")
    @GetMapping(value = "/get-status/{id}/{type}")
    public PayloadResponse<String> getMediaUploadStatus(
            @Parameter(required = true, description = "ID of the search media") @PathVariable Long id,
            @Parameter(required = true, description = "Type of media") @PathVariable String type) throws ApiException {
        var req = new LoV(id, type);
        return fileUploadSegmentService.getMediaStatus(new EntityRequest<>(req));
    }
}
