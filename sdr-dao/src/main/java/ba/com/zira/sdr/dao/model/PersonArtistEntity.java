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
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_person_artist" database table.
 *
 */
@Entity
@Table(name = "sat_person_artist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "PersonArtistEntity.findAll", query = "SELECT p FROM PersonArtistEntity p")
public class PersonArtistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_PERSON_ARTIST_ID_GENERATOR", sequenceName = "SAT_PERSON_ARTIST_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_PERSON_ARTIST_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "end_of_relationship")
    private LocalDateTime endOfRelationship;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "start_of_relaltionship")
    private LocalDateTime startOfRelaltionship;

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to ArtistEntity
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    // bi-directional many-to-one association to PersonEntity
    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

}