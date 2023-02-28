package ba.com.zira.sdr.api.model.connectedmediadetail;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties needed to create a Connected Media Detail")
public class ConnectedMediaDetailCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(0)
    @Schema(description = "Id of the object")
    private Long objectId;
    @NotBlank
    @Schema(description = "Type of the object")
    private String objectType;
    @Schema(description = "Connection link")
    private String connectionLink;
    @Schema(description = "Connection source")
    private String connectionSource;
    @Schema(description = "Connection type")
    private String connectionType;

}
