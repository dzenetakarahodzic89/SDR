package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.CountryEntity;

@Repository
public class CountryDAO extends AbstractDAO<CountryEntity, Long> {

    public Map<Long, String> getFlagAbbs(final List<Long> ids) {
        var hql = new StringBuilder(
                "select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.flagAbbriviation) from CountryEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }

    public List<CountryResponse> getAllCountries() {
        var hql = "select new ba.com.zira.sdr.api.model.country.CountryResponse(r.id, r.name, r.flagAbbriviation, r.region) from CountryEntity r order by r.name";
        TypedQuery<CountryResponse> query = entityManager.createQuery(hql, CountryResponse.class);
        return query.getResultList();
    }

    public List<LoV> getAllCountriesExceptOneWithTheSelectedId(final Long id) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.name) from CountryEntity m where m.id!= :id";
        var q = entityManager.createQuery(hql, LoV.class).setParameter("id", id);
        return q.getResultList();
    }

}
