package ba.com.zira.sdr.dao.model;

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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_genre" database table.
 *
 */
@Entity
@Table(name = "sat_genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "GenreEntity.findAll", query = "SELECT g FROM GenreEntity g")
public class GenreEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_GENRE_ID_GENERATOR", sequenceName = "SAT_GENRE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_GENRE_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "information")
    private String information;

    @Column(name = "modified")
    private Timestamp modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    // bi-directional many-to-one association to GenreEntity
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity mainGenre;

    // bi-directional many-to-one association to GenreEntity
    @OneToMany(mappedBy = "mainGenre")
    private List<GenreEntity> subGenres;

    // bi-directional many-to-one association to SongEntity
    @OneToMany(mappedBy = "genre")
    private List<SongEntity> songs;

}