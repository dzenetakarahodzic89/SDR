package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an Deezer response")
public class DeezerIntegration implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the Deezer integration")
    private Long id;
    @Schema(description = "Name of the Deezer integration")
    private String name;
    @Schema(description = "Creation date of the Deezer integration")
    private LocalDateTime created;
    @Schema(description = "User that created the Deezer integration")
    private String createdBy;
    @Schema(description = "Last modification date of the Deezer integration")
    private LocalDateTime modified;
    @Schema(description = "User that modified the Deezer integration")
    private String modifiedBy;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description ="Request of the Deezer integration")
    private String request;
    @Schema(description="Response of the Deezer integration")
    private String response;

}