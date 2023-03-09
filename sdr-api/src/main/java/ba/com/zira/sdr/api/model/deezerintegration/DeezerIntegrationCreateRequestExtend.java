package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a deezer integration with more fields")
public class DeezerIntegrationCreateRequestExtend implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime created;

    private String createdBy;

    private String name;

    private String request;

    private String response;

    private String status;

    private Long objectId;

    private String objectType;
}
