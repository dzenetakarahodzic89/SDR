package ba.com.zira.sdr.api.model.generateplaylist;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for generating a playlist")
public class SavePlaylistRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of the playlist")
    @NotBlank
    private String name;

    @Schema(description = "List of song id-s")
    private List<Long> songIds;
}
