package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.countryrelations.CountryRelationResponse;
import ba.com.zira.sdr.dao.model.CountryRelationEntity;

@Repository
public class CountryRelationsDAO extends AbstractDAO<CountryRelationEntity, Long> {

    public List<CountryRelationResponse> getCountryRelationByCountryId(final Long countryId) {
        var hql = "select new ba.com.zira.sdr.api.model.countryrelations.CountryRelationResponse(cr.id, cr.created, cr.createdBy, cr.modified, cr.modifiedBy, cr.country.id, cr.countryRelation) from CountryRelationEntity cr where cr.country.id =:countryId";
        TypedQuery<CountryRelationResponse> q = entityManager.createQuery(hql, CountryRelationResponse.class).setParameter("countryId",
                countryId);
        return q.getResultList();
    }

    public CountryRelationEntity relationByCountryId(final Long countryId) {
        var hql = "select cr from CountryRelationEntity cr where cr.country.id = :countryId";
        TypedQuery<CountryRelationEntity> q = entityManager.createQuery(hql, CountryRelationEntity.class).setParameter("countryId",
                countryId);
        return q.getSingleResult();
    }

    public CountryRelationEntity getRelationForCountry(Long countryId) {
        var hql = "select sc from CountryRelationEntity sc where sc.country.id = :id";
        TypedQuery<CountryRelationEntity> q = entityManager.createQuery(hql, CountryRelationEntity.class).setParameter("id", countryId);
        return q.getSingleResult();
    }
}
