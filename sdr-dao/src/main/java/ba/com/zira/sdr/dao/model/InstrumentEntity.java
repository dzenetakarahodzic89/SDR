package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_instrument" database table.
 *
 */
@Entity
@Table(name = "sat_instrument")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "InstrumentEntity.findAll", query = "SELECT i FROM InstrumentEntity i")
public class InstrumentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SAT_INSTRUMENT_ID_GENERATOR", sequenceName = "SAT_INSTRUMENT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_INSTRUMENT_ID_GENERATOR")
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

	@Column(name = "type")
	private String type;

	@Column(name = "outline_text")
	private String outlineText;

	// bi-directional many-to-one association to NoteSheetEntity
	@OneToMany(mappedBy = "instrument")
	private List<NoteSheetEntity> noteSheets;

	// bi-directional many-to-one association to SongInstrumentEntity
	@OneToMany(mappedBy = "instrument")
	private List<SongInstrumentEntity> songInstruments;

}