package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of artist find by person response")
public class ArtistPersonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    private Long id;
    @Schema(description = "Name of the artist")
    private String name;
    @Schema(description = "Creation date")
    private String created;
    @Schema(description = "User that created the artist")
    private String createdBy;
    @Schema(description = "Date of birth")
    private LocalDateTime dateOfBirth;
    @Schema(description = "Date of death")
    private LocalDateTime dateOfDeath;
    @Schema(description = "Information about the artist")
    private String information;
    @Schema(description = "Date of modification")
    private LocalDateTime modified;
    @Schema(description = "User that modified the artist")
    private String modifiedBy;
    @Schema(description = "Status of the artist")
    private String status;
    @Schema(description = "Surname of the artist")
    private String surname;
    @Schema(description = "Type of the artist")
    private String type;
    @Schema(description = "Person-artist")
    private Map<Long, String> personArtistNames;
    @Schema(description = "Song-artist")
    private Map<Long, String> songArtistNames;

    public ArtistPersonResponse(final Long id, final String name, final LocalDateTime created, final String createdBy,
            final LocalDateTime dateOfBirth, final LocalDateTime dateOfDeath, final String information, final LocalDateTime modified,
            final String modifiedBy, final String status, final String surname, final String type) {
        super();
        this.id = id;
        this.name = name;
        this.created = created != null ? created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        this.createdBy = createdBy;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.information = information;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.status = status;
        this.surname = surname;
        this.type = type;
    }
}