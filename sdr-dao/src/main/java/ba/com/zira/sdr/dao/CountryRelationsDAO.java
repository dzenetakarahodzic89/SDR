package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.CountryRelationEntity;

@Repository
public class CountryRelationsDAO extends AbstractDAO<CountryRelationEntity, Long> {

    public List<CountryRelationEntity> findByCountryAndRelation(Long countryId, String relation) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CountryRelationEntity> query = cb.createQuery(CountryRelationEntity.class);
        Root<CountryRelationEntity> root = query.from(CountryRelationEntity.class);
        query.select(root)
                .where(cb.and(cb.equal(root.get("country").get("id"), countryId), cb.equal(root.get("countryRelation"), relation)));
        return entityManager.createQuery(query).getResultList();
    }

}
