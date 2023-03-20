package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a artist")
public class ArtistRequestSuperclass implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the artist")
    protected String name;
    @Schema(description = "Date of birth")
    protected LocalDateTime dateOfBirth;
    @Schema(description = "Date of death")
    protected LocalDateTime dateOfDeath;
    @Schema(description = "Information about the artist")
    protected String information;
    @Schema(description = "Status of the artist")
    protected String status;
    @Schema(description = "Surname of the artist")
    protected String surname;
    @Schema(description = "Type of the artist")
    protected String type;

}