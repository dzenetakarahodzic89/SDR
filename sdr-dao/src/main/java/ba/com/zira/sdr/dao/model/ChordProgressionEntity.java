package ba.com.zira.sdr.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_chord_progression" database table.
 *
 */
@Entity
@Table(name = "sat_chord_progression")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "ChordProgressionEntity.findAll", query = "SELECT c FROM ChordProgressionEntity c")
public class ChordProgressionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_CHORD_PROGRESSION_ID_GENERATOR", sequenceName = "SAT_CHORD_PROGRESSION_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_CHORD_PROGRESSION_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "information")
    private String information;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to SongEntity
    @OneToMany(mappedBy = "chordProgression")
    private List<SongEntity> songs;

}