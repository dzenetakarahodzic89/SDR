package ba.com.zira.sdr.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.CountryEntity;

@Repository
public class CountryDAO extends AbstractDAO<CountryEntity, Long> {

    public Map<Long, String> getFlagAbbs(List<Long> ids) {
        var hql = new StringBuilder(
                "select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.flagAbbriviation) from CountryEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }
}
