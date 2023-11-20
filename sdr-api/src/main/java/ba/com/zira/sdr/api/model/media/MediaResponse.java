package ba.com.zira.sdr.api.model.media;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MediaResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime modified;
    private String modifiedBy;
    private Long objectId;
    private String objectType;
}