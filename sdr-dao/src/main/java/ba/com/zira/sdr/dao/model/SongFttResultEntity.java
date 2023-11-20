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
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the "sat_song_ftt_result" database table.
 *
 */
@Entity
@Table(name = "sat_song_ftt_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "SongFttResultEntity.findAll", query = "SELECT s FROM SongFttResultEntity s")
public class SongFttResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_SONG_FTT_RESULT_ID_GENERATOR", sequenceName = "SAT_SONG_FTT_RESULT_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_SONG_FTT_RESULT_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "fft_results")
    private String fftResults;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

}