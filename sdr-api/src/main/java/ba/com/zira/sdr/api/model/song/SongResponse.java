package ba.com.zira.sdr.api.model.song;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of song response")
public class SongResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Song id")
    private Long id;

    @NotBlank
    @Schema(description = "Name")
    private String name;

    @NotBlank
    @Schema(description = "Playtime")
    private String playtime;

    public SongResponse(@NotNull Long id, @NotBlank String name, @NotBlank String playtime) {
        super();
        this.id = id;
        this.name = name;
        this.playtime = playtime;
    }

}
