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
 * The persistent class for the "sat_battle_turn" database table.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sat_battle_turn")
@NamedQuery(name = "BattleTurnEntity.findAll", query = "SELECT c FROM BattleTurnEntity c")
public class BattleTurnEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_BATTLE_TURN_ID_GENERATOR", sequenceName = "SAT_BATTLE_TURN_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_BATTLE_TURN_ID_GENERATOR")
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

    @Column(name = "turn_number")
    private Long turnNumber;

    @Column(name = "map_state")
    private String mapState;

    @Column(name = "teams_state")
    private String teamState;

    @Column(name = "turn_combat_state")
    private String turnCombatState;

    @ManyToOne
    @JoinColumn(name = "battle_id")
    private BattleEntity battle;
}
