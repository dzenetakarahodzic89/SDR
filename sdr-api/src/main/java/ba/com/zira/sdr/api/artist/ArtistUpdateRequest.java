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
    private String name;
    private LocalDateTime dateOfBirth;
    private LocalDateTime dateOfDeath;
    private String information;
    private String status;
    private String surname;
    private String type;
}