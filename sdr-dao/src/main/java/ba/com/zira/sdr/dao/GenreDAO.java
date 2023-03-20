package ba.com.zira.sdr.dao;

import java.util.HashMap;
import java.util.List;
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

    public Boolean existsByName(final String name) {
        var hql = "select g from GenreEntity g where g.name = :name";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("name", name);
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Boolean existsByName(final String name, final Long id) {
        var hql = "select g from GenreEntity g where g.name = :name and g.id != :id";
        TypedQuery<GenreEntity> q = entityManager.createQuery(hql, GenreEntity.class).setParameter("name", name).setParameter("id", id);
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Boolean subGenresExist(final Long id) {
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

    public Boolean songsExist(final Long id) {
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

    public Map<Long, String> subGenresByMainGenre(final Long mainGenreId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(g.id,g.name) from GenreEntity g where g.mainGenre.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", mainGenreId);
        try {
            return q.getResultStream().collect(Collectors.toMap(LoV::getId, LoV::getName));
        } catch (Exception e) {
            return new HashMap<>();
        }

    }

    public GenreEntity findByName(final String name) {
        var hql = "select g from GenreEntity g where lower(g.name) like lower(:name)";
        try {
            return entityManager.createQuery(hql, GenreEntity.class).setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<LoV> getSubGenreMainGenreNames() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(g.id,case when g.mainGenre=null then g.name"
                + " else concat(g.name,' - ', gg.name) end) from GenreEntity g left join GenreEntity gg on g.mainGenre.id=gg.id";
        return entityManager.createQuery(hql, LoV.class).getResultList();
    }

    public List<LoV> getMainGenreLoV() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(g.id,g.name) from GenreEntity g where g.mainGenre.id is null";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public List<LoV> getSubgenreLoV(final Long mainGenreId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(g.id,g.name) from GenreEntity g where g.mainGenre.id = :id";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class).setParameter("id", mainGenreId);
        return query.getResultList();
    }

    public List<LoV> getGenreLoV() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(g.id,g.name) from GenreEntity g";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

}
