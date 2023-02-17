package ba.com.zira.sdr.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * The persistent class for the "sat_itunes_int" database table.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_itunes_int")
@NamedQuery(name = "ItunesIntegrationEntity.findAll", query = "SELECT i FROM ItunesIntegrationEntity i")
public class ItunesIntegrationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_ITUNES_INT_ID_GENERATOR", sequenceName = "SAT_ITUNES_INT_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_ITUNES_INT_ID_GENERATOR")
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

    @Column(name = "name")
    private String name;

    @Column(name = "request")
    private String request;

    @Column(name = "response")
    private String response;

    @Column(name = "status")
    private String status;

}