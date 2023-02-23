package ba.com.zira.sdr.api;

import java.util.Map;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.sdr.api.model.image.ImageUploadRequest;

public interface FileUploadService {
    public Map<String, String> uploadImage(EntityRequest<ImageUploadRequest> request) throws ApiException;
}
