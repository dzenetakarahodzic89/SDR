package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a artist")
public class ArtistRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of the artist")
    protected String name;
    @Schema(description = "Surname of the artist")
    protected String surname;
    @Schema(description = "Full name of the artist")
    protected String fullName;
    @Schema(description = "Date of birth")
    protected LocalDateTime dateOfBirth;
    @Schema(description = "Date of death")
    protected LocalDateTime dateOfDeath;
    @Schema(description = "Information about the artist")
    protected String information;
    @Schema(description = "Type of the artist")
    protected String type;
    @Schema(description = "Outline text")
    protected String outlineText;

    private String coverImage;

    private String coverImageData;

    private List<Long> personIds;
}
