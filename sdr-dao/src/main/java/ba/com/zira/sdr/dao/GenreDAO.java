package ba.com.zira.sdr.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.GenreEntity;

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

}
