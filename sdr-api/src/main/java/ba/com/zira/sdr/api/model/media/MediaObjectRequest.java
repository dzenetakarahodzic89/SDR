package ba.com.zira.sdr.api.model.media;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MediaObjectRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long objectId;
    private String objectType;
}
