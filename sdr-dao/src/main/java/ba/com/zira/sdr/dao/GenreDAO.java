package ba.com.zira.sdr.dao;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.SongEntity;

@Repository
public class GenreDAO extends AbstractDAO<GenreEntity, Long> {

    public Boolean existsByName(String name) {
        var hql = "select g from GenreEntity g where g.name = :name";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("name", name);
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Boolean existsByName(String name, Long id) {
        var hql = "select g from GenreEntity g where g.name = :name and g.id != :id";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("name", name).setParameter("id", id);
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Boolean subGenresExist(Long id) {
        var hql = "select g from GenreEntity g where g.mainGenre.id = :id";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("id", id);
        try {

            q.getSingleResult();
            return true;
        } catch (NonUniqueResultException e) {
            return true;
        } catch (NoResultException e) {
            return false;
        }

    }

    public Boolean songsExist(Long id) {
        var hql = "select s from SongEntity s where s.genre.id = :id";
        TypedQuery<SongEntity> q = entityManager.createQuery(hql, SongEntity.class).setParameter("id", id);
        try {

            q.getSingleResult();
            return true;
        } catch (NonUniqueResultException e) {
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Map<Long, String> subGenresByMainGenre(Long mainGenreId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(g.id,g.name) from GenreEntity g where g.mainGenre.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", mainGenreId);
        try {
            return q.getResultStream().collect(Collectors.toMap(sl -> sl.getId(), sl -> sl.getName()));
        } catch (Exception e) {
            return null;
        }

    }

}
