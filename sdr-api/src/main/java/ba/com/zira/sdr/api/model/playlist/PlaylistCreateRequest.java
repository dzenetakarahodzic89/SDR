package ba.com.zira.sdr.api.model.playlist;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a playlist")
public class PlaylistCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the playlist")
    private String name;

}
