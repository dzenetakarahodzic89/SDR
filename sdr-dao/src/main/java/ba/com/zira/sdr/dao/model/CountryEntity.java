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
 * The persistent class for the "sat_country" database table.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_country")
@NamedQuery(name = "CountryEntity.findAll", query = "SELECT c FROM CountryEntity c")
public class CountryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_COUNTRY_ID_GENERATOR", sequenceName = "SAT_COUNTRY_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_COUNTRY_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "flag_abbriviation")
    private String flagAbbriviation;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private String region;

    @Column(name = "status")
    private String status;

    // bi-directional many-to-one association to SongArtistEntity
    @OneToMany(mappedBy = "country")
    private List<CountryRelationEntity> countries;
    
    @OneToMany(mappedBy = "country")
    private List<ReleaseCountryEntity> releaseCountries;

}