package ba.com.zira.sdr.api.model.spotifyintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an spotify integration response")
public class SpotifyIntegrationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the spotify integration")
    private Long id;
    @Schema(description = "Name of the spotify integration")
    private String name;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the spotify integration")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the spotify integration")
    private String modifiedBy;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Request of the spotify integration")
    private String request;
    @Schema(description = "Response of the spotify integration")
    private String response;
}
