package ba.com.zira.sdr.api.model.connectedmediadetail;

import java.io.Serializable;
import java.time.LocalDateTime;

import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMedia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a Connected Media Detail response")
public class ConnectedMediaDetail implements Serializable {

    @Schema(description = "Unique identifier of a connected media detail")
    private Long id;

    @Schema(description = "The data connection was established")
    private LocalDateTime connectionDate;

    @Schema(description = "Connection link")
    private String connectionLink;

    @Schema(description = "Connection source")
    private String connectionSource;

    @Schema(description = "Type of connection")
    private String connectionType;

    @Schema(description = "Creation date of connected media detail")
    private LocalDateTime created;

    @Schema(description = "User who created the connected media detail")
    private String createdBy;

    @Schema(description = "Date the connected media detail was last updated")
    private LocalDateTime modified;

    @Schema(description = "The last user who updated the connected media detail")
    private String modifiedBy;

    @Schema(description = "Status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "Main connected media object")
    private ConnectedMedia connectedMedia;
}
