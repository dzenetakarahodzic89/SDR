package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_album" database table.
 *
 */
@Entity
@Table(name = "sat_album")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "AlbumEntity.findAll", query = "SELECT a FROM AlbumEntity a")
public class AlbumEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_ALBUM_ID_GENERATOR", sequenceName = "SAT_ALBUM_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_ALBUM_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "date_of_release")
    private LocalDateTime dateOfRelease;

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

    @Column(name = "spotify_id")
    private Long spotifyId;

    // bi-directional many-to-one association to SongArtistEntity
    @OneToMany(mappedBy = "album")
    private List<SongArtistEntity> songArtists;

    // bi-directional many-to-one association to ConnectedMediaEntity
    @ManyToOne
    @JoinColumn(name = "era_id")
    private EraEntity era;

}