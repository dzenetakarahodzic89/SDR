package ba.com.zira.sdr.api.model.playlist;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a Playlist response")
public class Playlist implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Unique identifier of the playlist")
    private Long id;

    @Schema(description = "Name of the playlist")
    private String name;

    @Schema(description = "User code of the playlist")
    private String user_code;

    @Schema(description = "Information about the playlist")
    private String information;

    @NotBlank
    @Schema(description = "Creation date")
    private LocalDateTime created;

    @NotBlank
    @Schema(description = "User that created the playlist")
    private String createdBy;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the playlist")
    private String modifiedBy;

    @Schema(description = "The status", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "Number of plays")
    private Long number_of_plays;

    @Schema(description = "Number of shares")
    private Long number_of_shares;

    @Schema(description = "Total playtime")
    private Long total_playtime;

}
