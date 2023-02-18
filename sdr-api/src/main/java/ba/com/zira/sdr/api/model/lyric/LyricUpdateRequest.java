package ba.com.zira.sdr.api.model.lyric;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a lyric")
public class LyricUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the lyric")
    private Long id;
    @NotBlank
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @NotBlank
    @Schema(description = "User that created the lyric")
    private String createdBy;
    @NotBlank
    @Schema(description = "Language used in the lyric")
    private String language;
    @NotBlank
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @NotBlank
    @Schema(description = "User that modified the lyric")
    private String modifiedBy;
    @NotBlank
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @NotBlank
    @Schema(description = "The text of the song")
    private String text;

}