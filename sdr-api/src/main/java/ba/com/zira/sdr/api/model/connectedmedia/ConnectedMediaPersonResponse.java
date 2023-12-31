package ba.com.zira.sdr.api.model.connectedmedia;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a Connected Media response")
public class ConnectedMediaPersonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the connected media")
    private Long id;
    @Schema(description = "Creation date")
    private String created;
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
    @Schema(description = "Details")
    private List<ConnectedMediaDetailInfo> connectedMediaDetails;

    public ConnectedMediaPersonResponse(final Long id, final LocalDateTime created, final String createdBy, final LocalDateTime modified,
            final String modifiedBy, final Long objectId, final String objectType, final String status) {
        super();
        this.id = id;
        this.created = created != null ? created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.objectId = objectId;
        this.objectType = objectType;
        this.status = status;
    }

}
