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

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.instrument.InstrumentSearchResponse;
import ba.com.zira.sdr.api.instrument.InstrumentSongResponse;
import ba.com.zira.sdr.api.instrument.ResponseSongInstrumentEra;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.InstrumentEntity;
import ba.com.zira.sdr.dao.model.InstrumentEntity_;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity_;

@Repository
public class InstrumentDAO extends AbstractDAO<InstrumentEntity, Long> {

    public List<ResponseSongInstrumentEra> findAllSongsInErasForInstruments(Long instrumentId) {
        var hql = "select new ba.com.zira.sdr.api.instrument.ResponseSongInstrumentEra(e.name, COUNT(DISTINCT s.id))"
                + " from InstrumentEntity as i" + " join SongInstrumentEntity as si" + " on i.id =si.id" + " join SongEntity as s"
                + " on si.song.id = s.id" + " join SongArtistEntity as sa" + " on s.id = sa.song.id" + " join AlbumEntity as a"
                + " on sa.album.id = a.id" + " join EraEntity as e" + " on a.era.id = e.id" + " where i.id = :instrumentId"
                + " group by e.name";

        TypedQuery<ResponseSongInstrumentEra> query = entityManager.createQuery(hql, ResponseSongInstrumentEra.class)
                .setParameter("instrumentId", instrumentId);
        return query.getResultList();
    }

    public List<InstrumentSongResponse> getInstrumentsForSong(Long songId) {
        var hql = "select new ba.com.zira.sdr.api.instrument.InstrumentSongResponse(si.id,si.name) from SongInstrumentEntity ssi join SongEntity ss on ss.id = ssi.song.id join InstrumentEntity si on si.id = ssi.instrument.id where ss.id = :songId";
        TypedQuery<InstrumentSongResponse> q = entityManager.createQuery(hql, InstrumentSongResponse.class).setParameter("songId", songId);
        return q.getResultList();
    }

    public Map<Long, String> getInstrumentNames(List<Long> ids) {
        var hql = new StringBuilder(
                "select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.name) from InstrumentEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }

    public List<InstrumentEntity> find(String name, String sortBy) {

        final CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);

        final Root<InstrumentEntity> root = criteriaQuery.from(InstrumentEntity.class);
        Join<InstrumentEntity, SongInstrumentEntity> songInstruments = root.join(InstrumentEntity_.songInstruments);
        Join<SongInstrumentEntity, PersonEntity> persons = songInstruments.join(SongInstrumentEntity_.person);

        List<Predicate> predicates = new ArrayList<>();
        Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);
        Order order = null;

        if (name != null) {
            predicates.add(builder.like(root.get("name"), name));
        }

        if (sortBy != null) {
            switch (sortBy) {
            case "LastEdit":
                order = builder.desc(root.get("modified"));
                break;
            case "Alphabetical":
                order = builder.asc(root.get("name"));
                break;
            case "NoOfPersons":
                order = builder.desc(builder.count(persons));
                break;
            default:
                break;
            }
        }
        if (order != null) {
            criteriaQuery.multiselect(root, builder.count(persons)).where(predicateArray).groupBy(root.get("id")).orderBy(order);
        } else {
            criteriaQuery.multiselect(root, builder.count(persons)).where(predicateArray).groupBy(root.get("id"));
        }

        return entityManager.createQuery(criteriaQuery).getResultStream().map(r -> (InstrumentEntity) r.get(0))
                .collect(Collectors.toList());

    }

    public List<InstrumentSearchResponse> search(final String name, final String sortBy, final int page, final int pageSize) {
        var query = "select distinct new ba.com.zira.sdr.api.instrument.InstrumentSearchResponse(si.id, si.name, si.outlineText, si.modified, si.created) from InstrumentEntity si where"
                + " (si.name like :name or :name is null or :name = '')  ";

        if ("instrument_name".equals(sortBy)) {
            query += " order by si.name";
        } else if ("last_created".equals(sortBy)) {
            query += " order by si.created desc";
        }
        var q = entityManager.createQuery(query, InstrumentSearchResponse.class);

        if (name != null && !name.isEmpty()) {
            q.setParameter("name", "%" + name + "%");
        } else {
            q.setParameter("name", null);
        }

        int firstResult = (page - 1) * pageSize;
        int maxResults = pageSize;
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);
        return q.getResultList();

    }

}
