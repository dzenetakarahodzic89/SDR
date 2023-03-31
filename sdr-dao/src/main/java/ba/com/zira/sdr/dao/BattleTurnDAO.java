package ba.com.zira.sdr.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.battle.ArtistStructure;
import ba.com.zira.sdr.dao.model.BattleEntity;
import ba.com.zira.sdr.dao.model.BattleEntity_;
import ba.com.zira.sdr.dao.model.BattleTurnEntity;
import ba.com.zira.sdr.dao.model.BattleTurnEntity_;

@Repository
public class BattleTurnDAO extends AbstractDAO<BattleTurnEntity, Long> {

    public String getTeamStateJSONByBattleId(Long battleId) {
        var hql = "select bt.teamState from BattleTurnEntity bt where bt.battle.id=:battleId and bt.turnNumber=bt.battle.lastTurn";
        var q = entityManager.createQuery(hql, String.class).setParameter("battleId", battleId);

        return q.getSingleResult();
    }

    public List<ArtistStructure> getEligibleArtistsInformationByCountryId(List<Long> countryIds) {
        var subquery1 = "(select count (distinct sa2.id) from SongArtistEntity sa2 join ArtistEntity a2 on sa2.artist.id=a2.id where a2.id=a.id)";
        var subquery = "(select count (distinct sa.album.id) from SongArtistEntity sa join ArtistEntity a3 on sa.artist.id=a3.id where a3.id=a.id)";
        var hql = "select new ba.com.zira.sdr.api.model.battle.ArtistStructure(a.id,concat(coalesce(a.name,''),' ', coalesce(a.surname,'')),p.country.id,p.country.name,"
                + subquery1 + "," + subquery
                + ") from ArtistEntity a join PersonArtistEntity pa on a.id=pa.artist.id join PersonEntity p on pa.person.id=p.id where p.country.id in (:countryIds)";
        var q = entityManager.createQuery(hql, ArtistStructure.class).setParameter("countryIds", countryIds);

        return q.getResultList();

    }

    public void updateTeamStateOfLastTurn(String teamStateJSON, Long battleId, String userCode) {
        var hql1 = "select bt.id from BattleTurnEntity bt where bt.battle.id = :battleId and bt.turnNumber=bt.battle.lastTurn";
        var turnId = entityManager.createQuery(hql1, Long.class).setParameter("battleId", battleId).getSingleResult();

        var hql2 = "update BattleTurnEntity bt " + "set bt.teamState = :teamStateJSON, " + "bt.modified = :timestamp,"
                + " bt.modifiedBy = :userCode " + "where bt.id = :turnId";
        var timestamp = LocalDateTime.now();
        var q = entityManager.createQuery(hql2).setParameter("userCode", userCode).setParameter("turnId", turnId)
                .setParameter("teamStateJSON", teamStateJSON).setParameter("timestamp", timestamp);
        q.executeUpdate();
    }

    public List<BattleTurnEntity> getByBattleId(Long battleId) {

        final CriteriaQuery<BattleTurnEntity> criteriaQuery = builder.createQuery(BattleTurnEntity.class);
        final Root<BattleTurnEntity> root = criteriaQuery.from(BattleTurnEntity.class);

        Join<BattleTurnEntity, BattleEntity> battles = root.join(BattleTurnEntity_.battle);

        criteriaQuery.where(builder.equal(battles.get(BattleEntity_.id), battleId));
        criteriaQuery.select(root).distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public BattleTurnEntity getFullBattleTurn(Long battleId) {
        var hql = "select bt from BattleTurnEntity bt where bt.battle.id=:battleId order by bt.turnNumber desc";
        TypedQuery<BattleTurnEntity> q = entityManager.createQuery(hql, BattleTurnEntity.class).setParameter("battleId", battleId)
                .setMaxResults(1);

        return q.getSingleResult();
    }
}
