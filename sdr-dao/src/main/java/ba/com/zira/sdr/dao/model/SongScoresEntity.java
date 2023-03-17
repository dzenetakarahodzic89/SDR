package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sat_user_recommendation_integration_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "SongScoresEntity.findAll", query = "SELECT a FROM SongScoresEntity a")
public class SongScoresEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_SONG_SCORES_ID_GENERATOR", sequenceName = "SAT_SONG_SCORES_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_SONG_SCORES_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private SongEntity song;

    @Column(name = "sdr_score")
    private Long sdrScore;

    @Column(name = "spotify_score")
    private Long spotifyScore;

    @Column(name = "deezer_score")
    private Long deezerScore;

    @Column(name = "youtube_music_score")
    private Long youtubeMusicScore;

    @Column(name = "tidal_score")
    private Long tidalScore;

    @Column(name = "itunes_score")
    private Long itunesScore;

    @Column(name = "google_play_score")
    private Long googlePlayScore;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    @Column(name = "playtime_in_seconds")
    private Long playtimeInSeconds;
}
