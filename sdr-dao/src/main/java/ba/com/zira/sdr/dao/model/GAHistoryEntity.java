package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sat_ga_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "GAHistoryEntity.findAll", query = "SELECT a FROM GAHistoryEntity a")
public class GAHistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_GA_RESULTS_ID_GENERATOR", sequenceName = "SAT_GA_RESULTS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_GA_RESULTS_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "population_size")
    private Long populationSize;

    @Column(name = "number_of_iterations")
    private Long numberOfIterations;

    @Column(name = "max_fitness")
    private Double maxFitness;

    @Column(name = "fitness_progress")
    private String fitnessProgress;

    @Column(name = "playlist_id")
    private Long playlistId;
}
