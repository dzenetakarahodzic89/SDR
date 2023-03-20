package ba.com.zira.sdr.api.artist;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ArtistResponse extends ArtistResponseSuperclass {

    private static final long serialVersionUID = 1L;
    @Schema(description = "Outline text for the artist")
    private String outlineText;

    public ArtistResponse(String name, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath, String information, String status,
            String surname, String type) {
        super(name, dateOfBirth, dateOfDeath, information, status, surname, type);

    }

    public ArtistResponse(Long id, String name, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath, String information, String status,
            String surname, String type) {
        super(id, name, dateOfBirth, dateOfDeath, information, status, surname, type);

    }

    public ArtistResponse(Long id, String name, String created, String createdBy, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath,
            String information, LocalDateTime modified, String modifiedBy, String status, String surname, String type,
            Map<Long, String> personArtistNames, Map<Long, String> songArtistNames, String outlineText) {
        super(id, name, created, createdBy, dateOfBirth, dateOfDeath, information, modified, modifiedBy, status, surname, type,
                personArtistNames, songArtistNames);
        this.outlineText = outlineText;

    }
}
