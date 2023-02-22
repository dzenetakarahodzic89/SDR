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
 * The persistent class for the "sat_song_artist" database table.
 *
 */
@Entity
@Table(name = "sat_song_artist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "SongArtistEntity.findAll", query = "SELECT s FROM SongArtistEntity s")
public class SongArtistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_SONG_ARTIST_ID_GENERATOR", sequenceName = "SAT_SONG_ARTIST_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_SONG_ARTIST_ID_GENERATOR")
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

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to AlbumEntity
    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    // bi-directional many-to-one association to ArtistEntity
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    // bi-directional many-to-one association to LabelEntity
    @ManyToOne
    @JoinColumn(name = "label_id")
    private LabelEntity label;

    // bi-directional many-to-one association to SongEntity
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

}