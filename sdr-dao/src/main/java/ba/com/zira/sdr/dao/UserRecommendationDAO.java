package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.userrecommendation.AverageScorePerCountry;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;

@Repository
public class UserRecommendationDAO extends AbstractDAO<UserRecommendationEntity, Long> {

    public List<UserRecommendationResponse> findAllUsers() {
        var hql = "select new ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse(r.id, r.userCode) from UserRecommendationEntity r ";
        TypedQuery<UserRecommendationResponse> query = entityManager.createQuery(hql, UserRecommendationResponse.class);
        return query.getResultList();
    }

    public List<UserRecommendationResponse> averageScoreByGenre(final List<Long> userIds) {

        var hql = "select new ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse(r.id, sg.name, r.userCode,  round(avg(surd.userScore), 2), "
                + "MAX(ss.name)) " + "from UserRecommendationEntity r "
                + "inner join UserRecommendationDetailEntity surd on r.id = surd.userRecommendation.id "
                + "inner join SongEntity ss on surd.song.id=ss.id " + "inner join GenreEntity sg on ss.genre.id = sg.id "
                + "where r.id in :userIds " + "group by r.id, sg.name order by sg.name, r.userCode asc";

        var q = entityManager.createQuery(hql, UserRecommendationResponse.class);
        q.setParameter("userIds", userIds);
        q.setMaxResults(100);

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

    public Boolean existsUserCode(String userCode) {
        var hql = "select u from UserRecommendationEntity u where u.userCode=:userCode";
        var q = entityManager.createQuery(hql, UserRecommendationEntity.class).setParameter("userCode", userCode).setMaxResults(1);

        try {
            q.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
