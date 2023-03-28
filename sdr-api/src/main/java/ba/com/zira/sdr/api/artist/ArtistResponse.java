package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ArtistResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    protected Long id;
    @Schema(description = "Name of the artist")
    protected String name;
    @Schema(description = "Creation date")
    protected String created;
    @Schema(description = "User that created the artist")
    protected String createdBy;
    @Schema(description = "Date of birth")
    protected LocalDateTime dateOfBirth;
    @Schema(description = "Date of death")
    protected LocalDateTime dateOfDeath;
    @Schema(description = "Information about the artist")
    protected String information;
    @Schema(description = "Date of modification")
    protected LocalDateTime modified;
    @Schema(description = "User that modified the artist")
    protected String modifiedBy;
    @Schema(description = "Status of the artist")
    protected String status;
    @Schema(description = "Surname of the artist")
    protected String surname;
    @Schema(description = "Type of the artist")
    protected String type;
    @Schema(description = "Person-artist")
    protected transient Map<Long, String> personArtistNames;
    @Schema(description = "Song-artist")
    protected transient Map<Long, String> songArtistNames;
    @Schema(description = "Outline text for the artist")
    protected String outlineText;

    public ArtistResponse(final Long id, final String name, final LocalDateTime created, final String createdBy,
            final LocalDateTime dateOfBirth, final LocalDateTime dateOfDeath, final String information, final LocalDateTime modified,
            final String modifiedBy, final String status, final String surname, final String type) {
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

    public ArtistResponse(String name, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath, String information, String status,
            String surname, String type) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.information = information;
        this.status = status;
        this.surname = surname;
        this.type = type;
    }

    public ArtistResponse(Long id, String name, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath, String information, String status,
            String surname, String type) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.information = information;
        this.status = status;
        this.surname = surname;
        this.type = type;
    }

    public ArtistResponse(Long id, String name) {
        this.id = id;
        this.name = name;

    }
}
