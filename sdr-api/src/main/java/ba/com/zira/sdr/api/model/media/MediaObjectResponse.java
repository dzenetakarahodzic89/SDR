package ba.com.zira.sdr.api.model.media;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MediaObjectResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long objectId;
    private String objectType;
    private String objectName;
    private List<MediaStoreResponse> mediaStores;

    public MediaObjectResponse(final Long objectId, final String objectType) {
        super();
        this.objectId = objectId;
        this.objectType = objectType;
    }

    public MediaObjectResponse(final Long objectId, final String objectType, final String objectName) {
        super();
        this.objectId = objectId;
        this.objectType = objectType;
        this.objectName = objectName;
    }

    public MediaObjectResponse() {

    }

}
