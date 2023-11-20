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

/**
 * The persistent class for the "sat_lyrics" database table.
 *
 */
@Entity
@Table(name = "sat_lyrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "LyricEntity.findAll", query = "SELECT l FROM LyricEntity l")
public class LyricEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_LYRICS_ID_GENERATOR", sequenceName = "SAT_LYRICS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_LYRICS_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "text")
    private String text;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

}