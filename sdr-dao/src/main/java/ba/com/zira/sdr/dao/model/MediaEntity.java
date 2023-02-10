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
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_media" database table.
 *
 */
@Entity
@Table(name = "sat_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "MediaEntity.findAll", query = "SELECT m FROM MediaEntity m")
public class MediaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_MEDIA_ID_GENERATOR", sequenceName = "SAT_MEDIA_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_MEDIA_ID_GENERATOR")
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

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "object_type")
    private String objectType;

    // bi-directional many-to-one association to MediaStoreEntity
    @OneToMany(mappedBy = "media")
    private List<MediaStoreEntity> mediaStores;

}