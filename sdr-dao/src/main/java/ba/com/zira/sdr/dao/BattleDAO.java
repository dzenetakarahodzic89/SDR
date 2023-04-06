package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.battle.BattleSingleResponse;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.dao.model.BattleEntity;

@Repository
public class BattleDAO extends AbstractDAO<BattleEntity, Long> {
    public BattleSingleResponse findLastBattleTurn(Long id) {
        var hql = "select new ba.com.zira.sdr.api.model.battle.BattleSingleResponse(sb.id,sb.name, sbt.turnNumber, sbt.mapState, sbt.teamState,sbt.turnCombatState,sbt.id,sbt.status) from BattleEntity sb join BattleTurnEntity sbt on sb.id =sbt.battle where sb.id = :id order by sbt.turnNumber desc";
        TypedQuery<BattleSingleResponse> query = entityManager.createQuery(hql, BattleSingleResponse.class).setParameter("id", id)
                .setMaxResults(1);
        return query.getSingleResult();
    }

    public List<Long> getActiveCountries(Long teamSize) {
        var hqlCountries = "select c.id from CountryEntity c "
                + "join PersonEntity p on c.id = p.country.id join PersonArtistEntity pa on p.id = pa.person.id "
                + "join ArtistEntity a on pa.artist.id = a.id group by c.id, c.name having count(a.id) >= :teamSize";
        TypedQuery<Long> queryCountry = entityManager.createQuery(hqlCountries, Long.class).setParameter("teamSize", teamSize);
        return queryCountry.getResultList();
    }

    public List<CountryResponse> getNumberOfActiveCountries(List<Long> getCountries, Long songSize) {

        var hql = "select new ba.com.zira.sdr.api.model.country.CountryResponse(c.id, c.name, count(c.id)) " + "from CountryEntity c "
                + "join PersonEntity p on c.id = p.country.id " + "join PersonArtistEntity pa on p.id = pa.person.id "
                + "join ArtistEntity a on pa.artist.id = a.id " + "join SongArtistEntity sa on a.id = sa.artist.id "
                + "join SongEntity s on s.id = sa.song.id where (c.id) in (:countries) "
                + "group by c.id, c.name having count(c.id) >= :songSize " + "order by c.name asc";
        TypedQuery<CountryResponse> query = entityManager.createQuery(hql, CountryResponse.class).setParameter("songSize", songSize)
                .setParameter("countries", getCountries);
        return query.getResultList();
    }

}
