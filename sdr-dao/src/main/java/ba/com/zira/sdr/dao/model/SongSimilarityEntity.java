package ba.com.zira.sdr.dao.model;

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

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the "sat_song_similaity" database table.
 *
 */
@Entity
@Table(name = "sat_song_similarity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "SongSimilarityEntity.findAll", query = "SELECT s FROM SongSimilarityEntity s")
public class SongSimilarityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_SONG_SIMILAITY_ID_GENERATOR", sequenceName = "SAT_SONG_SIMILARITY_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_SONG_SIMILAITY_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "total_similarity_score")
    private BigDecimal totalSimilarityScore;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_a_id")
    private SongEntity songA;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_b_id")
    private SongEntity songB;

    // bi-directional many-to-one association to SongSimilarityDetailEntity
    @OneToMany(mappedBy = "songSimilarity")
    private List<SongSimilarityDetailEntity> songSimilarityDetails;

}