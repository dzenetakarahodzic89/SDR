package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a Genre response")
public class Genre implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the genre")
    private Long id;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the genre")
    private String createdBy;
    @Schema(description = "Information about the genre")
    private String information;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the genre")
    private String modifiedBy;
    @Schema(description = "Name of the genre")
    private String name;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Type")
    private String type;
    @Schema(description = "Main genre")
    private Genre mainGenre;
    @Schema(description = "Subgenres")
    private Map<Long, String> subGenreNames;
    @Schema(description = "Songs")
    private Map<Long, String> songNames;
}
