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
 * The persistent class for the "sat_user_recommendation" database table.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_user_recommendation")
@NamedQuery(name = "UserRecommendationEntity.findAll", query = "SELECT u FROM UserRecommendationEntity u")
public class UserRecommendationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_USER_RECOMMENDATION_ID_GENERATOR", sequenceName = "SAT_USER_RECOMMENDATION_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_USER_RECOMMENDATION_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "user_code")
    private String userCode;

    // bi-directional many-to-one association to UserRecommendationDetailEntity
    @OneToMany(mappedBy = "userRecommendation")
    private List<UserRecommendationDetailEntity> userRecommendationDetails;

}