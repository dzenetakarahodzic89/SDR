package ba.com.zira.sdr.api.model.era;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import ba.com.zira.sdr.api.artist.ArtistSongResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a era response")
public class EraSearchResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String scope;
    private LocalDateTime modified;
    @Schema(description = "Genre Id")
    private Long genreId;
    private String imageUrl;
    // private String outlineText;
    @Schema(description = "Album name")
    private String album;
    private String information;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Schema(description = "Genre name")
    private String genre;
    @Schema(description = "List of artists")

    private List<ArtistSongResponse> artists;

    public EraSearchResponse(Long id, String name, String scope, LocalDateTime modified
    /*
     * String information, LocalDateTime startDate, LocalDateTime endDate,
     * String genreName, Long genreId
     */) {
        this.id = id;
        this.name = name;
        this.scope = scope;
        this.modified = modified;

        // this.outlineText = outlineText;
        // this.information = information;
        // this.startDate = startDate;
        // this.endDate = endDate;
        // this.genre = genreName;
        // this.genreId = genreId;

    }

}
