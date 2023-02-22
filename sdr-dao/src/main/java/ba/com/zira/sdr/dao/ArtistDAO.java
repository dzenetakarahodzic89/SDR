package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity;

@Repository
public class ArtistDAO extends AbstractDAO<ArtistEntity, Long> {

    public List<ArtistResponse> getAllArtists(String name) {
        var hql = "select new ba.com.zira.sdr.api.artist.ArtistResponse(r.id, r.name, r.dateOfBirth,"
                + " r.dateOfDeath, r.information, r.status, r.surname, r.type) from ArtistEntity r where r.name like :name";
        TypedQuery<ArtistResponse> query = entityManager.createQuery(hql, ArtistResponse.class).setParameter("name", '%' + name + '%');
        return query.getResultList();
    }

    public List<ArtistResponse> getAllArtists() {
        var hql = "select new ba.com.zira.sdr.api.artist.ArtistResponse(r.id, r.name, r.dateOfBirth,"
                + " r.dateOfDeath, r.information, r.status, r.surname, r.type) from ArtistEntity r";
        TypedQuery<ArtistResponse> query = entityManager.createQuery(hql, ArtistResponse.class);
        return query.getResultList();
    }

    public Boolean songArtistExist(Long id) {
        var hql = "select s from SongArtistEntity s where s.artist.id = :id";
        TypedQuery<SongArtistEntity> q = entityManager.createQuery(hql, SongArtistEntity.class).setParameter("id", id);
        try {

            q.getSingleResult();
            return true;
        } catch (NonUniqueResultException e) {
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Boolean personArtistExist(Long id) {
        var hql = "select s from PersonArtistEntity s where s.artist.id = :id";
        TypedQuery<PersonArtistEntity> q = entityManager.createQuery(hql, PersonArtistEntity.class).setParameter("id", id);
        try {
            q.getSingleResult();
            return true;
        } catch (NonUniqueResultException e) {
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /*
     * public List<ArtistEntity> findArtistsByEra(Long eraId) {
     * TypedQuery<ArtistEntity> query = entityManager.createQuery(
     * "SELECT a FROM ArtistEntity a JOIN a.songArtists sa JOIN sa.album era WHERE era.id = :eraId"
     * , ArtistEntity.class); query.setParameter("eraId", eraId); return
     * query.getResultList(); }
     * 
     * 
     * public Map<String, Object> getArtistsByEra(Long eraId) {
     * TypedQuery<ArtistEntity> query = entityManager.
     * createQuery("SELECT DISTINCT a FROM ArtistEntity a JOIN a.artistPersons "
     * +
     * "ap JOIN ap.person p JOIN a.songArtists sa JOIN sa.album alb WHERE alb.era.id = :eraId"
     * , ArtistEntity.class); query.setParameter("eraId", eraId);
     * List<ArtistEntity> artists = query.getResultList(); List<ArtistResponse>
     * soloArtists = new ArrayList<>(); List<ArtistResponse> groups = new
     * ArrayList<>(); for (ArtistEntity artist : artists) { Map<Long, String>
     * personArtistNames = new HashMap<>(); if (artist.getPersonArtists().size()
     * == 1) { // solo artist PersonArtistEntity ap =
     * artist.getPersonArtists().get(0);
     * personArtistNames.put(ap.getPerson().getId(), ap.getPerson().getName());
     * soloArtists.add(new ArtistResponse(artist.getId(), artist.getName(),
     * artist.getCreated(), artist.getCreatedBy(), artist.getDateOfBirth(),
     * artist.getDateOfDeath(), artist.getInformation(), artist.getModified(),
     * artist.getModifiedBy(), artist.getStatus(), artist.getSurname(),
     * artist.getType(), personArtistNames, null)); } else { // group for
     * (PersonArtistEntity ap : artist.getPersonArtists()) {
     * personArtistNames.put(ap.getPerson().getId(), ap.getPerson().getName());
     * } groups.add(new ArtistResponse(artist.getId(), artist.getName(),
     * artist.getCreated(), artist.getCreatedBy(), artist.getDateOfBirth(),
     * artist.getDateOfDeath(), artist.getInformation(), artist.getModified(),
     * artist.getModifiedBy(), artist.getStatus(), artist.getSurname(),
     * artist.getType(), personArtistNames, null)); } } Map<String, Object>
     * response = new HashMap<>(); response.put("soloArtists", soloArtists);
     * response.put("groups", groups); return response; }
     * 
     */
}
