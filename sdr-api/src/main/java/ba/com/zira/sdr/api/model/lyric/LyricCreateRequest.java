package ba.com.zira.sdr.api.model.lyric;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a lyric")
public class LyricCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Min(1)
    @Schema(description = "Unique identifier of the lyric")
    private Long id;
    @NotBlank
    @Schema(description = "Language used in the lyric")
    private String language;
    @NotBlank
    @Schema(description = "The text of the song")
    private String text;
    @NotNull
    @Schema(description = "ID of the song")
    private Long songID;

}