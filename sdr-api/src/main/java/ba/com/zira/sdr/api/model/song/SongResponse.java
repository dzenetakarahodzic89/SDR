package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @Schema(description = "Song genre")
    private String genreName;

    private Long genreId;

    private String audioUrl;
    private String coverUrl;

    public SongResponse(@NotNull final Long id, @NotBlank final String name, @NotBlank final String playtime,
            @NotBlank final String genreName) {
        super();
        this.id = id;
        this.name = name;
        this.playtime = playtime;
        this.genreName = genreName;
    }

}
