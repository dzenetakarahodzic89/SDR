package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumPersonResponse;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaPersonResponse;
import ba.com.zira.sdr.api.model.song.SongPersonResponse;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentPersonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a person overview response")
public class PersonOverviewResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "Person id")
    private Long id;

    @Schema(description = "created")
    private LocalDateTime created;

    @Schema(description = "created_by")
    private String createdBy;

    @Schema(description = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Schema(description = "date_of_death")
    private LocalDateTime dateOfDeath;

    @Schema(description = "gender")
    private String gender;

    @Schema(description = "information")
    private String information;

    @Schema(description = "modified")
    private LocalDateTime modified;

    @Schema(description = "modified_by")
    private String modifiedBy;

    @Schema(description = "name")
    private String name;

    @Schema(description = "status")
    private String status;

    @Schema(description = "surname")
    private String surname;

    @Schema(description = "outline_text")
    private String outlineText;
    @Schema(description = "imageUrl")
    private String imageUrl;

    @Schema(description = "countryId")
    private Long countryId;
    @Schema(description = "flagAbbreviation")
    private String flagAbbreviation;

    @Schema(description = "List of artists")
    private List<ArtistResponse> artists;

    @Schema(description = "List of albums")
    private List<AlbumPersonResponse> albums;
    @Schema(description = "List of songs")
    private List<SongPersonResponse> songs;

    @Schema(description = "List of songs")
    private List<ConnectedMediaPersonResponse> connectedMedia;

    @Schema(description = "List of song instruments")
    private List<SongInstrumentPersonResponse> instruments;

    public PersonOverviewResponse(final Long id, final LocalDateTime created, final String createdBy, final LocalDateTime dateOfBirth,
            final LocalDateTime dateOfDeath, final String gender, final String information, final LocalDateTime modified,
            final String modifiedBy, final String name, final String surname, final String outlineText, final Long countryId,
            final String flagAbbreviation) {
        super();
        this.id = id;
        this.created = created;
        this.createdBy = createdBy;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
        this.information = information;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.name = name;
        this.surname = surname;
        this.outlineText = outlineText;
        this.countryId = countryId;
        this.flagAbbreviation = flagAbbreviation;
    }

}
