package ba.com.zira.sdr.dao.model;

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

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_user_recommendation_detail" database table.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_user_recommendation_integration_detail")
@NamedQuery(name = "UserRecommendationIntegrationDetailEntity.findAll", query = "SELECT u FROM UserRecommendationDetailEntity u")
public class UserRecommendationIntegrationDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_USER_RECOMMENDATION_INTEGRATION_DETAIL_ID_GENERATOR",
            sequenceName = "SAT_USER_RECOMMENDATION_INTEGRATION_DETAIL_SEQ", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_USER_RECOMMENDATION_INTEGRATION_DETAIL_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "sdr_score")
    private BigDecimal sdrScore;

    @Column(name = "spotify_score")
    private BigDecimal spotifyScore;

    @Column(name = "deezer_score")
    private BigDecimal deezerScore;

    @Column(name = "tidal_score")
    private BigDecimal tidalScore;

    @Column(name = "itunes_score")
    private BigDecimal iTunesScore;

    @Column(name = "google_play_score")
    private BigDecimal googlePlayScore;

    @Column(name = "youtube_music_score")
    private BigDecimal youtubeMusicScore;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "playtime_in_seconds")
    private Long playtimeInSeconds;

    // bi-directional many-to-one association to UserRecommendationEntity
    @ManyToOne
    @JoinColumn(name = "user_recommendation_id")
    private UserRecommendationEntity userRecommendation;

    // bi-directional many-to-one association to UserRecommendationEntity
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

}