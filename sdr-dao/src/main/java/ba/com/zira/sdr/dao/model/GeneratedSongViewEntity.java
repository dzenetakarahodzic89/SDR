package ba.com.zira.sdr.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Immutable
@Table(name = "sat_generated_songs_view")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "GeneratedSongViewEntity.findAll", query = "SELECT a FROM GeneratedSongViewEntity a")
public class GeneratedSongViewEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "song")
    private String song;

    @Column(name = "artists")
    private String artists;

    @Column(name = "albums")
    private String albums;

    @Column(name = "genre")
    private String genre;

    @Column(name = "countries")
    private String countries;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "cover_id")
    private Long coverId;

    @Column(name = "remix_id")
    private Long remixId;
}
