package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.EraEntity;

@Repository
public class EraDAO extends AbstractDAO<EraEntity, Long> {
    public List<LoV> getAllErasLoV() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from EraEntity s";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }
}
