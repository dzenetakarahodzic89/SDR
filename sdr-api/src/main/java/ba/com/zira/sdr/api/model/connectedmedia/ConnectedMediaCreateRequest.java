package ba.com.zira.sdr.api.model.connectedmedia;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a connected media")
public class ConnectedMediaCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(0)
    @Schema(description = "Id of the object")
    private Long objectId;

    @NotBlank
    @Schema(description = "Type of the object")
    private String objectType;

}
