package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentCreateRequest;

public interface FileUploadSegmentService {
    PayloadResponse<String> create(EntityRequest<FileUploadSegmentCreateRequest> request) throws ApiException;
}
