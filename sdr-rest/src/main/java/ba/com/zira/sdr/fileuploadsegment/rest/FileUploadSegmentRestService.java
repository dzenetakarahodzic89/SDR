package ba.com.zira.sdr.fileuploadsegment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.FileUploadSegmentService;
import ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
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
}
