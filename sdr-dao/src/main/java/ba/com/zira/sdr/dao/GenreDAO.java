package ba.com.zira.sdr.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.GenreEntity;
import ba.com.zira.sdr.dao.model.SongEntity;

@Repository
public class GenreDAO extends AbstractDAO<GenreEntity, Long> {

    public boolean existsByName(String name) {
        var hql = "select g from GenreEntity g where g.name = :name";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("name", name);
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean existsByName(String name, Long id) {
        var hql = "select g from GenreEntity g where g.name = :name and g.id != :id";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("name", name).setParameter("id", id);
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean subGenresExist(Long id) {
        var hql = "select g from GenreEntity g where g.mainGenre.id = :id";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("id", id);

        try {
            q.setFirstResult(0);
            q.setMaxResults(1);
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }

    }

    public boolean songsExist(Long id) {
        var hql = "select s from SongEntity s where s.genre.id = :id";
        TypedQuery<SongEntity> q = entityManager.createQuery(hql, SongEntity.class).setParameter("id", id);

        try {
            q.setFirstResult(0);
            q.setMaxResults(1);
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
