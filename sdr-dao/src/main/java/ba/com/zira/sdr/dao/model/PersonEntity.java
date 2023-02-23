package ba.com.zira.sdr.dao.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The persistent class for the "sat_person" database table.
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

    @Column(name = "outline_text")
    private String outlineText;

    @Column(name = "country_id")
    private Long countryId;

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