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
 * The persistent class for the "sat_artist" database table.
 *
 */
@Entity
@Table(name = "sat_artist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "ArtistEntity.findAll", query = "SELECT a FROM ArtistEntity a")
public class ArtistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_ARTIST_ID_GENERATOR", sequenceName = "SAT_ARTIST_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_ARTIST_ID_GENERATOR")
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

    @Column(name = "type")
    private String type;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "outline_text")
    private String outlineText;

    // bi-directional many-to-one association to PersonArtistEntity
    @OneToMany(mappedBy = "artist")
    private List<PersonArtistEntity> personArtists;

    // bi-directional many-to-one association to SongArtistEntity
    @OneToMany(mappedBy = "artist")
    private List<SongArtistEntity> songArtists;

}