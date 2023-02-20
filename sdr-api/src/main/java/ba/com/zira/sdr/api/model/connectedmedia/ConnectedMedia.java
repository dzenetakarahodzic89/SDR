package ba.com.zira.sdr.api.model.connectedmedia;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a Connected Media response")
public class ConnectedMedia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the connected media")
    private Long id;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the connected media")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the connected media")
    private String modifiedBy;
    @Schema(description = "Id of the object")
    private Long objectId;
    @Schema(description = "Type of the object")
    private String objectType;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;

}
