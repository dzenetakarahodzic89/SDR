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
 * The persistent class for the "sat_connected_media" database table.
 *
 */
@Entity
@Table(name = "sat_connected_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "ConnectedMediaEntity.findAll", query = "SELECT c FROM ConnectedMediaEntity c")
public class ConnectedMediaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_CONNECTED_MEDIA_ID_GENERATOR", sequenceName = "SAT_CONNECTED_MEDIA_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_CONNECTED_MEDIA_ID_GENERATOR")
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

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to ConnectedMediaDetailEntity
    @OneToMany(mappedBy = "connectedMedia")
    private List<ConnectedMediaDetailEntity> connectedMediaDetails;

}