package ba.com.zira.sdr.api.model.playlist;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Search filters for playlist")
public class PlaylistSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of the playlist")
    private String name;

    @Schema(description = "Id of the song")
    private Long songId;

    @Schema(description = "Id of the genre")
    private Long genreId;

    @Schema(description = "Sorting method", allowableValues = { "NoOfSongs", "Alphabetical", "LastEdit" })
    private String sortBy;

}
