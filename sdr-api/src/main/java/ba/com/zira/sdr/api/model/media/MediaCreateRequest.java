package ba.com.zira.sdr.api.model.media;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String objectType;
    private Long objectId;
    private String mediaObjectData;
    private String mediaObjectName;
    private String mediaObjectType;
    private String mediaStoreType;
    private String mediaFileType;

}