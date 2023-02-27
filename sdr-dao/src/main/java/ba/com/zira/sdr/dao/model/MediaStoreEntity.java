package ba.com.zira.sdr.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the sat_media_store database table.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_media_store")
@NamedQuery(name = "MediaStoreEntity.findAll", query = "SELECT m FROM MediaStoreEntity m")
public class MediaStoreEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    private String data;

    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    private String name;

    private String type;

    private String url;

    private String extension;

    // bi-directional many-to-one association to MediaEntity
    @ManyToOne
    @JoinColumn(name = "media_id")
    private MediaEntity media;

}