package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.label.LabelArtistResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.LabelEntity_;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.PersonEntity_;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity_;

@Repository
public class LabelDAO extends AbstractDAO<LabelEntity, Long> {


    public List<LabelEntity> findLabelsByNameFounder(String name, Long founder, String sortBy) {

        final CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
        final Root<LabelEntity> root = criteriaQuery.from(LabelEntity.class);
        Join<LabelEntity, PersonEntity> founders = root.join(LabelEntity_.founder);
        Join<LabelEntity,SongArtistEntity> artists = root.join(LabelEntity_.songArtists);
        Join<SongArtistEntity, ArtistEntity> artist = artists.join(SongArtistEntity_.artist);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(builder.like(root.get("name"), name));
        }

        if (founder != null) {
            Predicate[] subqueryPredicates = new Predicate[2];

            Subquery<LabelEntity> subquery = criteriaQuery.subquery(LabelEntity.class);
            Root<LabelEntity> founderPerson = subquery.from(LabelEntity.class);
            subqueryPredicates[0] = builder.equal(builder.literal(founder), founderPerson.get(LabelEntity_.founder).get(PersonEntity_.id));
            subqueryPredicates[1] = founderPerson.in(founders);
            subquery.select(founderPerson).where(subqueryPredicates);
            predicates.add(builder.exists(subquery));
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);
        Order order = null;

        if (sortBy != null) {
            switch (sortBy) {
            case "LastEdit":
                order = builder.desc(root.get("modified"));
                break;
            case "Alphabetical":
                order = builder.asc(root.get("name"));
                break;
            case "NoOfArtists":
                order = builder.desc(builder.count(artist));
                break;
            default:
                break;
            }

        }

        if (order != null) {
            criteriaQuery.multiselect(root, builder.count(artist)).where(predicateArray).groupBy(root.get("id")).orderBy(order);
        } else {
            criteriaQuery.multiselect(root, builder.count(artist)).where(predicateArray).groupBy(root.get("id"));
        }

        return entityManager.createQuery(criteriaQuery).getResultStream().map(r -> (LabelEntity) r.get(0)).collect(Collectors.toList());

    }

    public LabelArtistResponse getById(Long labelId) {
        var hql = "select new ba.com.zira.sdr.api.model.label.LabelArtistResponse(la.id, la.name, la.outlineText,la.information,la.foundingDate,f.name || ' ' || f.surname,f.id) from LabelEntity la left join PersonEntity f on la.founder.id = f.id where la.id =:id";
        TypedQuery<LabelArtistResponse> q = entityManager.createQuery(hql, LabelArtistResponse.class).setParameter("id", labelId);
        return q.getSingleResult();
    }

    public Map<Long, String> getLabelNames(List<Long> ids) {
        var hql = new StringBuilder("select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.name) from LabelEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }

    public List<LoV> getLabelLoVs() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(l.id,l.name) from LabelEntity l";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }


}
