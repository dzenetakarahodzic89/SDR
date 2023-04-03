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

@Entity
@Table(name = "sat_mb_release")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "ReleaseEntity.findAll", query = "SELECT r FROM ReleaseEntity r")
public class ReleaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_MB_RELEASE_ID_GENERATOR", sequenceName = "SAT_RELEASE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_MB_RELEASE_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "gid")
    private String gid;

    @Column(name = "name")
    private String name;

    @Column(name = "artist_credit")
    private Long artistCredit;

    @Column(name = "release_group")
    private Long releaseGroup;

    @Column(name = "status")
    private Long status;

    @Column(name = "packaging")
    private Long packaging;

    @Column(name = "language")
    private Long language;

    @Column(name = "script")
    private Long script;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "comment")
    private String comment;

    @Column(name = "edits_pending")
    private Long editsPending;

    @Column(name = "quality")
    private Long quality;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "release")
    private List<ReleaseCountryEntity> releaseCountries;

}
