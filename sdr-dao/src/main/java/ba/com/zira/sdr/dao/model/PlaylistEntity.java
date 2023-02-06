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
 * The persistent class for the "sat_playlist" database table.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_playlist")
@NamedQuery(name = "PlaylistEntity.findAll", query = "SELECT p FROM PlaylistEntity p")
public class PlaylistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_PLAYLIST_ID_GENERATOR", sequenceName = "SAT_PLAYLIST_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_PLAYLIST_ID_GENERATOR")
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

    @Column(name = "number_of_plays")
    private Long numberOfPlays;

    @Column(name = "number_of_shares")
    private Long numberOfShares;

    @Column(name = "status")
    private String status;

    @Column(name = "total_playtime")
    private Long totalPlaytime;

    @Column(name = "user_code")
    private String userCode;

    // bi-directional many-to-one association to SongPlaylistEntity
    @OneToMany(mappedBy = "playlist")
    private List<SongPlaylistEntity> songPlaylists;

}