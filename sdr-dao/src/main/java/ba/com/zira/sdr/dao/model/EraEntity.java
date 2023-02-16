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
 * The persistent class for the "sat_era" database table.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_era")
@NamedQuery(name = "EraEntity.findAll", query = "SELECT e FROM EraEntity e")
public class EraEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_ERA_ID_GENERATOR", sequenceName = "SAT_ERA_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_ERA_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "information")
    private String information;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "scope")
    private String scope;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to ConnectedMediaDetailEntity
    @OneToMany(mappedBy = "era")
    private List<AlbumEntity> albums;

}