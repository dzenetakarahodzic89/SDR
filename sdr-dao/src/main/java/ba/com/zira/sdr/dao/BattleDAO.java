package ba.com.zira.sdr.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.dao.model.BattleEntity;

@Repository
public class BattleDAO extends AbstractDAO<BattleEntity, Long> {
    public BattleSingleResponse findLastBattleTurn(Long id) {
        var hql = "select new ba.com.zira.sdr.api.model.battle.BattleSingleResponse(sb.name, sbt.turnNumber, sbt.mapState, sbt.teamState) from BattleEntity sb join BattleTurnEntity sbt on sb.id =sbt.battle where sb.id = :id order by sbt.turnNumber desc";
        TypedQuery<BattleSingleResponse> query = entityManager.createQuery(hql, BattleSingleResponse.class).setParameter("id", id)
                .setMaxResults(1);
        return query.getSingleResult();
    }
}
