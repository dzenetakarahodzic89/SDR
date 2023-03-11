package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the "sat_song" database table.
 *
 */
@Entity
@Table(name = "sat_song")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "SongEntity.findAll", query = "SELECT s FROM SongEntity s")
public class SongEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_SONG_ID_GENERATOR", sequenceName = "SAT_SONG_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_SONG_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "date_of_release")
    private LocalDateTime dateOfRelease;

    @Column(name = "information")
    private String information;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "playtime")
    private String playtime;

    @Column(name = "playtime_in_seconds")
    private Long playtimeInSeconds;

    @Column(name = "status")
    private String status;

    @Column(name = "outline_text")
    private String outlineText;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "spotify_status")
    private String spotifyStatus;

    @Column(name = "deezer_status")
    private String deezerStatus;

    @Column(name = "deezer_id")
    private String deezerId;

    // bi-directional many-to-one association to LyricEntity
    @OneToMany(mappedBy = "song")
    private List<LyricEntity> lyrics;

    // bi-directional many-to-one association to NoteSheetEntity
    @OneToMany(mappedBy = "song")
    private List<NoteSheetEntity> noteSheets;

    // bi-directional many-to-one association to ChordProgressionEntity
    @ManyToOne
    @JoinColumn(name = "chord_progression_id")
    private ChordProgressionEntity chordProgression;

    // bi-directional many-to-one association to GenreEntity
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "cover_id")
    private SongEntity cover;

    // bi-directional many-to-one association to SongEntity
    @OneToMany(mappedBy = "cover")
    private List<SongEntity> covers;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "remix_id")
    private SongEntity remix;

    // bi-directional many-to-one association to SongEntity
    @OneToMany(mappedBy = "remix")
    private List<SongEntity> remixes;

    // bi-directional many-to-one association to SongArtistEntity
    @OneToMany(mappedBy = "song")
    private List<SongArtistEntity> songArtists;

    // bi-directional many-to-one association to SongFttResultEntity
    @OneToMany(mappedBy = "song")
    private List<SongFttResultEntity> songFttResults;

    // bi-directional many-to-one association to SongInstrumentEntity
    @OneToMany(mappedBy = "song")
    private List<SongInstrumentEntity> songInstruments;

    // bi-directional many-to-one association to SongPlaylistEntity
    @OneToMany(mappedBy = "song")
    private List<SongPlaylistEntity> songPlaylists;

    // bi-directional many-to-one association to SongSimilarityEntity
    @OneToMany(mappedBy = "songA")
    private List<SongSimilarityEntity> songSimilarityForA;

    // bi-directional many-to-one association to SongSimilarityEntity
    @OneToMany(mappedBy = "songB")
    private List<SongSimilarityEntity> songSimilarityForB;

    // bi-directional many-to-one association to UserRecommendationDetailEntity
    @OneToMany(mappedBy = "song")
    private List<UserRecommendationDetailEntity> userRecommendationDetails;

}