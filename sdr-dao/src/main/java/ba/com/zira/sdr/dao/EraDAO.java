package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.era.EraSearchResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.EraEntity;

@Repository
public class EraDAO extends AbstractDAO<EraEntity, Long> {
    public List<LoV> getAllErasLoV() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from EraEntity s";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public List<EraSearchResponse> find(String name, String sortBy, int page, int pageSize, List<Long> albumIds, List<Long> artistIds,
            List<Long> genreIds) {
        var query = "select new ba.com.zira.sdr.api.model.era.EraSearchResponse(se.id, se.name, se.scope, se.modified) from EraEntity se inner join AlbumEntity sa on se.id = sa.era.id inner join SongArtistEntity ssa on sa.id=ssa.album.id  inner join SongEntity ss on ssa.song.id =ss.id "
                + " where (se.name like :name or :name is null or :name = '') and (coalesce(:albumIds, null) is null or sa.id in :albumIds)  and (coalesce(:artistIds, null) is null or ssa.id in :artistIds) and (coalesce(:genreIds, null) is null or ss.genre.id in :genreIds)  ";
        if ("last_date".equals(sortBy)) {
            query += " order by se.modified desc";
        }

        else {
            query += " order by se.name";
        }

        var q = entityManager.createQuery(query, EraSearchResponse.class);

        if (name != null && !name.isEmpty()) {
            q.setParameter("name", "%" + name + "%");
        } else {
            q.setParameter("name", null);
        }
        if (albumIds != null) {
            q.setParameter("albumIds", albumIds);
        } else {
            q.setParameter("albumIds", null);
        }
        if (artistIds != null) {
            q.setParameter("artistIds", artistIds);
        } else {
            q.setParameter("artistIds", null);
        }

        if (genreIds != null) {
            q.setParameter("genreIds", genreIds);
        } else {
            q.setParameter("genreIds", null);
        }

        // Apply pagination
        int firstResult = (page - 1) * pageSize;
        int maxResults = pageSize;
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);

        return q.getResultList();

    }

}
