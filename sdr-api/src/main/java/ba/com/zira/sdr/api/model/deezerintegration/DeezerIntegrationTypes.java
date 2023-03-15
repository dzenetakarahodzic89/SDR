package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DeezerIntegrationTypes implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tableName;
    private LocalDateTime lastModified;
    private Long sequence;
    private Boolean isFinished;
}
