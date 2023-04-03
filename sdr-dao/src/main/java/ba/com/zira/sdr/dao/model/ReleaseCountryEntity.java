package ba.com.zira.sdr.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sat_mb_release_country")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "ReleaseCountryEntity.findAll", query = "SELECT rc FROM ReleaseCountryEntity rc")
public class ReleaseCountryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "release")
    private ReleaseEntity release;

    @ManyToOne
    @JoinColumn(name = "country")
    private CountryEntity country;

    @Column(name = "date_year")
    private Long dateYear;

    @Column(name = "date_month")
    private Long dateMonth;

    @Column(name = "date_day")
    private Long dateDay;

}
