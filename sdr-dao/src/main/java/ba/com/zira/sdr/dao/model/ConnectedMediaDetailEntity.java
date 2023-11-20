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
 * The persistent class for the "sat_connected_media_detail" database table.
 *
 */
@Entity
@Table(name = "sat_connected_media_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "ConnectedMediaDetailEntity.findAll", query = "SELECT c FROM ConnectedMediaDetailEntity c")
public class ConnectedMediaDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_CONNECTED_MEDIA_DETAIL_ID_GENERATOR", sequenceName = "SAT_CONNECTED_MEDIA_DETAIL_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_CONNECTED_MEDIA_DETAIL_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "connection_date")
    private LocalDateTime connectionDate;

    @Column(name = "connection_link")
    private String connectionLink;

    @Column(name = "connection_source")
    private String connectionSource;

    @Column(name = "connection_type")
    private String connectionType;

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

    // bi-directional many-to-one association to ConnectedMediaEntity
    @ManyToOne
    @JoinColumn(name = "connected_media_id")
    private ConnectedMediaEntity connectedMedia;

}