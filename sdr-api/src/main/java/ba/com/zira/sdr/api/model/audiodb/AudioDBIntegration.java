package ba.com.zira.sdr.api.model.audiodb;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Properties of an AudioDB response")
public class AudioDBIntegration implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the AudioDB integration")
    private Long id;

    @Schema(description = "Creation date of the AudioDB integration")
    private LocalDateTime created;

    @Schema(description = "User that created the AudioDB integration")
    private String createdBy;

    @Schema(description = "Last modification date of the AudioDB integration")
    private LocalDateTime modified;

    @Schema(description = "User that modified the AudioDB integration")
    private String modifiedBy;

    @Schema(description = "Name of the AudioDB integration")
    private String name;

    @Schema(description = "Request of the AudioDB integration")
    private String request;

    @Schema(description = "Response of the AudioDB integration")
    private String response;

    @Schema(description = "The status of the AudioDB integration", allowableValues = { "Inactive", "Active" })
    private String status;

}