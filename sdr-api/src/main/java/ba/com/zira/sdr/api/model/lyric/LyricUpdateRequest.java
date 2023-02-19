package ba.com.zira.sdr.api.model.lyric;

import java.io.Serializable;

import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a lyric")
public class LyricUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the lyric")
    private Long id;
    @Schema(description = "Language used in the lyric")
    private String language;
    @Schema(description = "The text of the song")
    private String text;
    @Schema(description = "ID of the song")
    private Long songId;

}