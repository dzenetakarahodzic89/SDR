package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_user_recommendation_integration_detail")
@NamedQuery(name = "UserRecommendationIntegrationDetailEntity.findAll", query = "SELECT u FROM UserRecommendationIntegrationDetailEntity u")
public class UserRecommendationIntegrationDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_USER_RECOMMENDATION_INTEGRATION_DETAIL_ID_GENERATOR",
            sequenceName = "SAT_USER_RECOMMENDATION_INTEGRATION_DETAIL_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_USER_RECOMMENDATION_INTEGRATION_DETAIL_ID_GENERATOR")
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

    @Column(name = "sdr_score")
    private Double sdrScore;

    @Column(name = "spotify_score")
    private Double spotifyScore;

    @Column(name = "deezer_score")
    private Double deezerScore;

    @Column(name = "youtube_music_score")
    private Double youtubeMusicScore;

    @Column(name = "tidal_score")
    private Double tidalScore;

    @Column(name = "itunes_score")
    private Double itunesScore;

    @Column(name = "google_play_score")
    private Double googlePlayScore;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "playtime_in_seconds")
    private Long playtimeInSeconds;

    // uni-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

}
