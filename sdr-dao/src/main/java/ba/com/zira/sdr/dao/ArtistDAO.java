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

}