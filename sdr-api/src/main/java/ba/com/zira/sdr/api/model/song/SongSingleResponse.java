package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import ba.com.zira.sdr.api.artist.ArtistSongResponse;
import ba.com.zira.sdr.api.instrument.InstrumentSongResponse;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentSingleSongResponse;
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
    @Schema(description = "Song id for which the current song is a remix of")
    private Long remixId;
    @Schema(description = "Song id for which the current song is a cover of")
    private Long coverId;
    @Schema(description = "Label name")
    private String label;
    @Schema(description = "Chord progression name")
    private String chordProgression;
    @Schema(description = "Chord progression id")
    private Long chordProgressionId;
    @Schema(description = "Genre Id")
    private Long genreId;
    @Schema(description = "Genre name")
    private String genre;
    @Schema(description = "Subgenre id")
    private Long subgenreId;
    private String spotifyId;
    @Schema(description = "Subgenres - format 1-rock")
    private Map<Long, String> subgenres;
    @Schema(description = "List of artists")
    private List<ArtistSongResponse> artists;
    private List<InstrumentSongResponse> instruments;
    private List<SongInstrumentSingleSongResponse> songInstruments;

    private String audioUrl;

    public SongSingleResponse(final Long id, final String songName, final String outlineText, final String information,
            final LocalDateTime dateOfRelease, final String playtime, final Long remixId, final Long coverId, final String chordName,
            final Long chordProgressionId, final String genreName, final Long genreId, String spotifyId) {
        this.id = id;
        this.name = songName;
        this.outlineText = outlineText;
        this.information = information;
        this.dateOfRelease = dateOfRelease;
        this.playtime = playtime;
        this.remixId = remixId;
        this.coverId = coverId;
        this.chordProgression = chordName;
        this.chordProgressionId = chordProgressionId;
        this.genre = genreName;
        this.genreId = genreId;
        this.spotifyId = spotifyId;
    }

    public SongSingleResponse(final Long id, final String songName, final String outlineText, final String information,
            final LocalDateTime dateOfRelease) {
        this.id = id;
        this.name = songName;
        this.outlineText = outlineText;
        this.information = information;
        this.dateOfRelease = dateOfRelease;

    }
}
