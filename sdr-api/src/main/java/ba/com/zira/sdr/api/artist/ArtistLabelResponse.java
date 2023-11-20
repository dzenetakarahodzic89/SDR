package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

public class ArtistLabelResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "Unique identifier")
    private Long id;
    @Schema(description = "Full artist  name")
    private String fullName;
    @Schema(description = "Full person name")
    private String personName;
    @Schema(description = "Date of birth")
    private String dateOfBirth;
    @Schema(description = "Album name")
    private String album;

    public ArtistLabelResponse(Long id, String fullName, String personName, LocalDateTime dateOfBirth, String album) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.personName = personName;
        this.dateOfBirth = dateOfBirth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.album = album;
    }

}
