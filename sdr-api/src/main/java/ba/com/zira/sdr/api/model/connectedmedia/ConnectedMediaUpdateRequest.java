package ba.com.zira.sdr.api.model.connectedmedia;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties to update a genre")
public class ConnectedMediaUpdateRequest implements Serializable {
    @Min(1)
    @Schema(description = "Id of the connected media")
    private Long id;

    @Min(1)
    @Schema(description = "Id of the object")
    private Long objectId;

    @NotBlank
    @Schema(description = "Type of the object")
    private String objectType;

}
