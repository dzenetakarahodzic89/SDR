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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the "sat_person" database table.
 *
 */
@Entity
@Table(name = "sat_person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "PersonEntity.findAll", query = "SELECT p FROM PersonEntity p")
public class PersonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_PERSON_ID_GENERATOR", sequenceName = "SAT_PERSON_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_PERSON_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "date_of_death")
    private LocalDateTime dateOfDeath;

    @Column(name = "gender")
    private String gender;

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

    @Column(name = "surname")
    private String surname;

    // bi-directional many-to-one association to LabelEntity
    @OneToMany(mappedBy = "founder")
    private List<LabelEntity> labels;

    // bi-directional many-to-one association to PersonArtistEntity
    @OneToMany(mappedBy = "person")
    private List<PersonArtistEntity> personArtists;

    // bi-directional many-to-one association to SongInstrumentEntity
    @OneToMany(mappedBy = "person")
    private List<SongInstrumentEntity> songInstruments;

}