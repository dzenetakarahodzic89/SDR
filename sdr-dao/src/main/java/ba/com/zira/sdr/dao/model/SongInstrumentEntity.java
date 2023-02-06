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
 * The persistent class for the "sat_song_instrument" database table.
 *
 */
@Entity
@Table(name = "sat_song_instrument")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "SongInstrumentEntity.findAll", query = "SELECT s FROM SongInstrumentEntity s")
public class SongInstrumentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_SONG_INSTRUMENT_ID_GENERATOR", sequenceName = "SAT_SONG_INSTRUMENT_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_SONG_INSTRUMENT_ID_GENERATOR")
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

    @Column(name = "name")
    private String name;

    // bi-directional many-to-one association to InstrumentEntity
    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private InstrumentEntity instrument;

    // bi-directional many-to-one association to PersonEntity
    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

}