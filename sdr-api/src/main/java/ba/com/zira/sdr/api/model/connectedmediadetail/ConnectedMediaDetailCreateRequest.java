package ba.com.zira.sdr.api.model.connectedmediadetail;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties needed to create a Connected Media Detail")
public class ConnectedMediaDetailCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Connection link")
    private String connectionLink;
    @Schema(description = "Connection source")
    private String connectionSource;
    @Schema(description = "Connection type")
    private String connectionType;
    @Schema(description = "Id of the connected media")
    private Long connectedMediaId;

}
