package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of song response")
public class SongResponse implements Serializable {

    private static final long serialVesrionUID = 1L;

    @NotNull
    @Schema(description = "Song id")
    private Long id;

    @NotBlank
    @Schema(description = "Created")
    private LocalDateTime created;

    @NotBlank
    @Schema(description = "Created by")
    private String createdBy;

    @NotBlank
    @Schema(description = "Date of release")
    private LocalDateTime dateOfRelease;

    @NotBlank
    @Schema(description = "Information")
    private String information;

    @NotBlank
    @Schema(description = "Modified")
    private LocalDateTime modified;

    @NotBlank
    @Schema(description = "Modified by")
    private String modifiedBy;

    @NotBlank
    @Schema(description = "Name")
    private String name;

    @NotBlank
    @Schema(description = "Playtime")
    private String playtime;

    @NotBlank
    @Schema(description = "Status")
    private String status;

}
