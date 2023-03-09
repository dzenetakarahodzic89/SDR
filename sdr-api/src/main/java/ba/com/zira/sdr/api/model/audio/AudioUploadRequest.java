package ba.com.zira.sdr.api.model.audio;

import java.io.Serializable;

import lombok.Data;

@Data
public class AudioUploadRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String audioName;
    private String audioData;
    private String fileType;

}
