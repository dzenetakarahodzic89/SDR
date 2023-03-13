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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the "sat_note_sheet" database table.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_note_sheet")
@NamedQuery(name = "NoteSheetEntity.findAll", query = "SELECT n FROM NoteSheetEntity n")
public class NoteSheetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_NOTE_SHEET_ID_GENERATOR", sequenceName = "SAT_NOTE_SHEET_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_NOTE_SHEET_ID_GENERATOR")
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

    @Column(name = "notation_type")
    private String notationType;

    @Column(name = "sheet_content")
    private String sheetContent;

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to InstrumentEntity
    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private InstrumentEntity instrument;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public void setSheetContent(String sheetContent2) {
        this.sheetContent = sheetContent2;

    }

}