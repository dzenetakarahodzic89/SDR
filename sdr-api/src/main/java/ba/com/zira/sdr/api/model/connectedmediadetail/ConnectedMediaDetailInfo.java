package ba.com.zira.sdr.api.model.connectedmediadetail;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a Connected Media response")
public class ConnectedMediaDetailInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the connected media detail")
    private Long id;
    @Schema(description = "Connection date")
    private LocalDateTime connectionDate;
    @Schema(description = "Connection link")
    private String connectionLink;
    @Schema(description = "Connection source")
    private String connectionSource;
    @Schema(description = "Connection type")
    private String connectionType;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the connected media detail")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the connected media detail")
    private String modifiedBy;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;

}
