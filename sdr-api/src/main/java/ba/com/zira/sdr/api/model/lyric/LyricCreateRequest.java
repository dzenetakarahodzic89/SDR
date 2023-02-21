package ba.com.zira.sdr.api.model.lyric;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a lyric")
public class LyricCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Language used in the lyric")
    private String language;
    @Schema(description = "The text of the song")
    private String text;
    @Schema(description = "ID of the song")
    private Long songId;

}