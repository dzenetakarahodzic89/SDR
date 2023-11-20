package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the "sat_song_similarity_detail" database table.
 *
 */
@Entity
@Table(name = "sat_song_similarity_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "SongSimilarityDetailEntity.findAll", query = "SELECT s FROM SongSimilarityDetailEntity s")
public class SongSimilarityDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_SONG_SIMILAITY_DETAIL_ID_GENERATOR", sequenceName = "SAT_SONG_SIMILARITY_DETAIL_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_SONG_SIMILAITY_DETAIL_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "grade")
    private BigDecimal grade;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "user_code")
    private String userCode;

    // bi-directional many-to-one association to SongSimilarityEntity
    @ManyToOne
    @JoinColumn(name = "song_similarity_id")
    private SongSimilarityEntity songSimilarity;

}