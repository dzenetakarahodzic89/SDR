package ba.com.zira.sdr.api;

import java.util.Map;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.sdr.api.model.audio.AudioUploadRequest;
import ba.com.zira.sdr.api.model.image.ImageUploadRequest;

/**
 * The Interface FileUploadService.
 */
public interface FileUploadService {

    /**
     * Upload image.
     *
     * @param request
     *            the request
     * @return the map
     * @throws ApiException
     *             the api exception
     */
    public Map<String, String> uploadImage(EntityRequest<ImageUploadRequest> request) throws ApiException;

    /**
     * Upload audio.
     *
     * @param request
     *            the request
     * @return the map
     * @throws ApiException
     *             the api exception
     */
    public Map<String, String> uploadAudio(EntityRequest<AudioUploadRequest> request) throws ApiException;
}
