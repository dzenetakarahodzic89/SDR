package ba.com.zira.sdr.core.utils;

import ba.com.zira.sdr.api.model.image.ImageCreateRequest;
import ba.com.zira.sdr.api.model.media.MediaCreateRequest;

public class MediaHelper {
    private MediaHelper() {
    }

    public static MediaCreateRequest addImage(ImageCreateRequest imageCreateRequest, Long id, String objectType) {
        if (imageCreateRequest != null && imageCreateRequest.getImageData() != null && imageCreateRequest.getImageName() != null) {

            var mediaRequest = new MediaCreateRequest();
            mediaRequest.setObjectType(objectType);
            mediaRequest.setObjectId(id);
            mediaRequest.setMediaObjectData(imageCreateRequest.getImageData());
            mediaRequest.setMediaObjectName(imageCreateRequest.getImageName());
            mediaRequest.setMediaStoreType("COVER_IMAGE");
            mediaRequest.setMediaObjectType("IMAGE");

            return mediaRequest;
        }
        return null;
    }
}
