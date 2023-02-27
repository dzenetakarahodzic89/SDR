package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistLabelResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "Unique identifier")
    private Long id;
    @Schema(description = "Full artist  name")
    private String name;
    @Schema(description = "Full person name")
    private String personName;
    @Schema(description = "Date of birth")
    private LocalDateTime dateOfBirth;
    @Schema(description = "Album name")
    private String album;

}
