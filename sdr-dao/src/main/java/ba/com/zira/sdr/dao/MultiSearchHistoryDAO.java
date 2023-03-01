package ba.com.zira.sdr.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.MultiSearchHistoryEntity;

@Repository
public class MultiSearchHistoryDAO extends AbstractDAO<MultiSearchHistoryEntity, String> {

    public MultiSearchHistoryEntity getLast() {
        var hql = "select max(smsh.refreshTime) from MultiSearchHistoryEntity smsh";
        TypedQuery<LocalDateTime> getTimeQuery = entityManager.createQuery(hql, LocalDateTime.class);
        LocalDateTime time = getTimeQuery.getSingleResult();
        hql = "select new ba.com.zira.sdr.dao.model.MultiSearchHistoryEntity(smsh.id, smsh.refreshTime, smsh.rowsBefore, smsh.rowsAfter, smsh.dataStructure) from MultiSearchHistoryEntity smsh where smsh.refreshTime = :refreshTime";
        TypedQuery<MultiSearchHistoryEntity> getEntityQuery = entityManager.createQuery(hql, MultiSearchHistoryEntity.class)
                .setParameter("refreshTime", time);
        return getEntityQuery.getSingleResult();
    }

    public List<MultiSearchHistoryEntity> getAllOrderedByRefreshTime() {
        var hql = "select new ba.com.zira.sdr.dao.model.MultiSearchHistoryEntity(r.id, r.refreshTime, r.rowsBefore, r.rowsAfter, r.dataStructure) from MultiSearchHistoryEntity r order by r.refreshTime desc";
        TypedQuery<MultiSearchHistoryEntity> query = entityManager.createQuery(hql, MultiSearchHistoryEntity.class).setMaxResults(7);
        return query.getResultList();
    }

}