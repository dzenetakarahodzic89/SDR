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
import ba.com.zira.sdr.api.model.person.PersonOverviewResponse;
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
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(p.id,p.name) from PersonEntity p "
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

    public List<LoV> getAllPersonsLoV() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.name) from PersonEntity s";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public PersonOverviewResponse getById(Long personId) {
        var hql = "select new ba.com.zira.sdr.api.model.person.PersonOverviewResponse(sp.id, sp.created, sp.createdBy, sp.dateOfBirth, sp.dateOfDeath, sp.gender, sp.information, sp.modified, sp.modifiedBy, sp.name,  sp.surname, sp.outlineText, sc.id, sc.flagAbbriviation) from PersonEntity sp inner join CountryEntity sc on sp.country.id = sc.id "
                + " where sp.id =:id";
        TypedQuery<PersonOverviewResponse> q = entityManager.createQuery(hql, PersonOverviewResponse.class).setParameter("id", personId);

        return q.getSingleResult();
    }

}
