package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import ba.com.zira.sdr.api.artist.ArtistSongResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a song response")
public class SongSingleResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String imageUrl;
    private String outlineText;
    @Schema(description = "Album name")
    private String album;
    private String information;
    private int playlistCount;
    private LocalDateTime dateOfRelease;
    private String playtime;
    private Long remixId;
    private Long coverId;
    @Schema(description = "Label name")
    private String label;
    @Schema(description = "Chord progression name")
    private String chordProgression;
    @Schema(description = "Genre Id")
    private Long genreId;
    @Schema(description = "Genre name")
    private String genre;
    private Long subgenreId;
    @Schema(description = "Subgenres - format 1-rock")
    private Map<Long, String> subgenres;
    @Schema(description = "List of artists")
    private List<ArtistSongResponse> artists;

    private String audioUrl;

    public SongSingleResponse(final Long id, final String songName, final String outlineText, final String information,
            final LocalDateTime dateOfRelease, final String playtime, final Long remixId, final Long coverId, final String chordName,
            final String genreName, final Long genreId) {
        this.id = id;
        this.name = songName;
        this.outlineText = outlineText;
        this.information = information;
        this.dateOfRelease = dateOfRelease;
        this.playtime = playtime;
        this.remixId = remixId;
        this.coverId = coverId;
        this.chordProgression = chordName;
        this.genre = genreName;
        this.genreId = genreId;
    }

    public SongSingleResponse(Long id, String songName, String outlineText, String information, LocalDateTime dateOfRelease) {
        this.id = id;
        this.name = songName;
        this.outlineText = outlineText;
        this.information = information;
        this.dateOfRelease = dateOfRelease;

    }
}
