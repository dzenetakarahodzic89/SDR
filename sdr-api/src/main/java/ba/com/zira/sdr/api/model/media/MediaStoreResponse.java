package ba.com.zira.sdr.api.model.media;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MediaStoreResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private LocalDateTime created;
    private String createdBy;
    private String data;
    private LocalDateTime modified;
    private String modifiedBy;
    private String name;
    private String type;
    private Long mediaId;
    private String url;
    private String extension;
}
