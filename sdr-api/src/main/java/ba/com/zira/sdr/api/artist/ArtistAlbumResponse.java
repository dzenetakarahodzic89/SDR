package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema
public class ArtistAlbumResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "Unique identifier")
    private Long id;
    @Schema(description = "Name of the artist")
    private String name;
    @Schema(description = "Date of birth")
    private LocalDateTime dateOfBirth;
    @Schema(description = "Unique identifier of the song")
    private Long albumCount;
    public ArtistAlbumResponse(Long id, String name, LocalDateTime dateOfBirth, Long albumCount) {
        super();
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.albumCount = albumCount;
    }

}
