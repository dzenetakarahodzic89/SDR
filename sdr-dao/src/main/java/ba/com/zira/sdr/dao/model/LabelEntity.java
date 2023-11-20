package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_label" database table.
 *
 */
@Entity
@Table(name = "sat_label")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "LabelEntity.findAll", query = "SELECT l FROM LabelEntity l")
public class LabelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_LABEL_ID_GENERATOR", sequenceName = "SAT_LABEL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_LABEL_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "founding_date")
    private LocalDateTime foundingDate;

    @Column(name = "information")
    private String information;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "outline_text")
    private String outlineText;

    // bi-directional many-to-one association to PersonEntity
    @ManyToOne
    @JoinColumn(name = "founder_id")
    private PersonEntity founder;

    // bi-directional many-to-one association to SongArtistEntity
    @OneToMany(mappedBy = "label")
    private List<SongArtistEntity> songArtists;


}