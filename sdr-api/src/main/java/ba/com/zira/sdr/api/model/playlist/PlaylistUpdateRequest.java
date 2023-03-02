package ba.com.zira.sdr.api.model.playlist;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a playlist")
public class PlaylistUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the playlist")
    private long id;

    @NotBlank
    @Schema(description = "Name of the playlist")
    private String name;

    @Schema(description = "Information about the playlist")
    private String information;

}
