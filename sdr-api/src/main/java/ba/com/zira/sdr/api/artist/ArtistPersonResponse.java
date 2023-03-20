package ba.com.zira.sdr.api.artist;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of artist find by person response")
public class ArtistPersonResponse extends ArtistResponseSuperclass {

    private static final long serialVersionUID = 1L;

    public ArtistPersonResponse(Long id, String name, LocalDateTime created, String createdBy, LocalDateTime dateOfBirth,
            LocalDateTime dateOfDeath, String information, LocalDateTime modified, String modifiedBy, String status, String surname,
            String type) {
        super(id, name, created, createdBy, dateOfBirth, dateOfDeath, information, modified, modifiedBy, status, surname, type);

    }

}