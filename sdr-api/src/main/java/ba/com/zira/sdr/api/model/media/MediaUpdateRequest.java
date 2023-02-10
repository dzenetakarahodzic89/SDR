package ba.com.zira.sdr.api.model.media;

import java.io.Serializable;

import lombok.Data;

@Data
public class MediaUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long objectId;
    private String objectType;
}