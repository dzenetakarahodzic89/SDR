package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.userrecommendation.AverageScorePerCountry;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.api.model.userrecommendation.UserScoreResponse;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;

@Repository
public class UserRecommendationDAO extends AbstractDAO<UserRecommendationEntity, Long> {

    public List<UserScoreResponse> findAllUsers() {
        var hql = "select new ba.com.zira.sdr.api.model.userrecommendation.UserScoreResponse(r.id,r.name, r.userCode) from UserRecommendationEntity r ";
        TypedQuery<UserScoreResponse> query = entityManager.createQuery(hql, UserScoreResponse.class);
        return query.getResultList();
    }

    public List<UserScoreResponse> averageScoreByGenre(final List<Long> userIds) {

        var hql = "select new ba.com.zira.sdr.api.model.userrecommendation.UserScoreResponse(r.id, r.name, r.userCode, round(avg(surd.userScore), 2), MAX(ss.name), sg.name) "
                + "from UserRecommendationEntity r " + "inner join r.userRecommendationDetails surd " + "inner join surd.song ss "
                + "inner join ss.genre sg " + "where r.id in :userIds " + "group by r.id, sg.name " + "order by sg.name, r.userCode asc";

        var q = entityManager.createQuery(hql, UserScoreResponse.class);
        q.setParameter("userIds", userIds);

        var results = q.getResultList();

        return results;

    }

    public List<AverageScorePerCountry> getAverageScorePerCountryForUser(String userCode, String recommendationService) {
        var serviceScore = "";
        var splitString = recommendationService.split(" ");
        if (splitString.length > 1) {
            serviceScore = "urid." + splitString[0].toLowerCase() + splitString[1] + "Score";
        } else {
            serviceScore = "urid." + splitString[0].toLowerCase() + "Score";
        }
        var hql = "select new ba.com.zira.sdr.api.model.userrecommendation.AverageScorePerCountry(c.name,avg(" + serviceScore + "))"
                + " from UserRecommendationIntegrationDetailEntity urid join UserRecommendationDetailEntity urd on urid.song.id = urd.song.id"
                + " join UserRecommendationEntity ur on urd.userRecommendation.id = ur.id join SongEntity s on urid.song.id=s.id"
                + " join SongArtistEntity sa on s.id=sa.song.id join ArtistEntity a on sa.artist.id=a.id join PersonArtistEntity pa"
                + " on a.id=pa.artist.id join PersonEntity p on pa.person.id=p.id join CountryEntity c on p.country.id=c.id"
                + " where ur.userCode = :userCode group by c.name";

        var q = entityManager.createQuery(hql, AverageScorePerCountry.class).setParameter("userCode", userCode);

        return q.getResultList();
    }

    public void cleanTableForGA() {
        var hql = "delete from UserRecommendationEntity d where d.createdBy = :ga";
        Query q = entityManager.createQuery(hql).setParameter("ga", "GA");
        q.executeUpdate();
    }

}
