package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a artist")
public class ArtistUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the artist")
    private long id;
    @Schema(description = "Name of the artist")
    private String name;
    @Schema(description = "Date of birth")
    private LocalDateTime dateOfBirth;
    @Schema(description = "Date of death")
    private LocalDateTime dateOfDeath;
    @Schema(description = "Information about the artist")
    private String information;
    @Schema(description = "Status of the artist")
    private String status;
    @Schema(description = "Surname of the artist")
    private String surname;
    @Schema(description = "Type of the artist")
    private String type;
}