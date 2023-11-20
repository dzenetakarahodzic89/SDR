package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ba.com.zira.sdr.api.model.song.SongResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of album by person response")
public class AlbumPersonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Album ID")
    private Long id;

    @Schema(description = "Release date of album")
    private String dateOfRelease;

    @Schema(description = "Informations about album")
    private String information;

    @Schema(description = "Album name")
    private String name;

    @Schema(description = "Album status")
    private String status;

    @Schema(description = "Album era")
    private String era;

    @Schema(description = "Artist name")
    private String artistName;

    @Schema(description = "Album songs")
    private List<SongResponse> songs;

    public AlbumPersonResponse(final Long id, final LocalDateTime dateOfRelease, final String information, final String name,
            final String status) {
        super();
        this.id = id;
        this.dateOfRelease = dateOfRelease != null ? dateOfRelease.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        this.information = information;
        this.name = name;
        this.status = status;
    }

}
