package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.person.PersonCountryResponse;
import ba.com.zira.sdr.api.model.person.PersonOverviewResponse;
import ba.com.zira.sdr.api.model.person.PersonSearchResponse;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity_;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import ba.com.zira.sdr.dao.model.PersonArtistEntity_;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.PersonEntity_;

@Repository
public class PersonDAO extends AbstractDAO<PersonEntity, Long> {

    public Map<Long, String> getPersonNames(List<Long> ids) {
        var hql = new StringBuilder("select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.name) from PersonEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }

    public List<LoV> personsByArtistId(Long artistId) {
        var hql = "select distinct new ba.com.zira.sdr.api.model.lov.LoV(p.id,p.name) from PersonEntity p "
                + "join PersonArtistEntity pa on p.id=pa.person.id join ArtistEntity a on pa.artist.id=a.id where a.id=:id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", artistId);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<PersonEntity> findAllByArtistId(Long artistId) {
        CriteriaQuery<PersonEntity> criteriaQuery = builder.createQuery(PersonEntity.class);
        final Root<PersonEntity> root = criteriaQuery.from(PersonEntity.class);
        final Join<PersonEntity, PersonArtistEntity> personArtists = root.join(PersonEntity_.personArtists);
        final Join<PersonArtistEntity, ArtistEntity> artists = personArtists.join(PersonArtistEntity_.artist);
        criteriaQuery.where(builder.equal(artists.get(ArtistEntity_.id), artistId));
        criteriaQuery.select(root).distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public Long getPersonsNumb() {
        var hql = "select count(sp.id) from PersonEntity sp";

        TypedQuery<Long> q = entityManager.createQuery(hql, Long.class);
        return q.getSingleResult();

    }

    public List<PersonCountryResponse> getPersonsAll() {
        var hql = "SELECT new ba.com.zira.sdr.api.model.person.PersonCountryResponse(sc.name, COUNT(sp.country.id)) FROM PersonEntity sp INNER JOIN CountryEntity sc ON sp.country.id = sc.id GROUP BY sp.country.id, sc.name";

        TypedQuery<PersonCountryResponse> q = entityManager.createQuery(hql, PersonCountryResponse.class);
        return q.getResultList();
    }

    public List<LoV> getAllPersonsLoV() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from PersonEntity s";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public List<LoV> getAllPersonsLoVs() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name,s.surname) from PersonEntity s";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public PersonOverviewResponse getById(Long personId) {
        var hql = "select new ba.com.zira.sdr.api.model.person.PersonOverviewResponse(sp.id, sp.created, sp.createdBy, sp.dateOfBirth, sp.dateOfDeath, sp.gender, sp.information, sp.modified, sp.modifiedBy, sp.name,  sp.surname, sp.outlineText, sc.id, sc.flagAbbriviation) from PersonEntity sp inner join CountryEntity sc on sp.country.id = sc.id "
                + " where sp.id =:id";
        TypedQuery<PersonOverviewResponse> q = entityManager.createQuery(hql, PersonOverviewResponse.class).setParameter("id", personId);

        return q.getSingleResult();
    }

    public List<PersonSearchResponse> personSearch(final String personName, final String sortBy, final String personGender, final int page,
            final int pageSize) {

        var query = "select new ba.com.zira.sdr.api.model.person.PersonSearchResponse(sp.id, sp.name, sp.surname, sp.outlineText, sp.gender, sp.modified) "
                + " from PersonEntity sp where (sp.name like :personName or :personName is null or :personName = '') and (sp.gender like :personGender or :personGender is null or :personGender = '') ";

        if ("alphabetical".equals(sortBy)) {
            query += " order by sp.name asc";

        } else if ("alphabetical_reverse".equals(sortBy)) {
            query += " order by sp.name desc";
        }

        else if ("last_edit".equals(sortBy)) {
            query += " order by sp.modified desc";
        }

        var q = entityManager.createQuery(query, PersonSearchResponse.class);

        if (personName != null && !personName.isEmpty()) {
            q.setParameter("personName", "%" + personName + "%");
        } else {
            q.setParameter("personName", null);
        }

        if (personGender != null && !personGender.isEmpty()) {
            q.setParameter("personGender", "%" + personGender + "%");
        } else {
            q.setParameter("personGender", null);
        }

        // Apply pagination
        int firstResult = (page - 1) * pageSize;
        int maxResults = pageSize;
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);

        return q.getResultList();
    }

}
