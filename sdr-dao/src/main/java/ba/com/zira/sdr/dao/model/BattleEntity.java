package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_battle" database table.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_battle")
@NamedQuery(name = "BattleEntity.findAll", query = "SELECT c FROM BattleEntity c")
public class BattleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_BATTLE_ID_GENERATOR", sequenceName = "SAT_BATTLE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_BATTLE_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

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

    @ManyToOne
    @JoinColumn(name = "winner_country_id")
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name = "last_turn")
    private BattleTurnEntity lastTurn;

}
