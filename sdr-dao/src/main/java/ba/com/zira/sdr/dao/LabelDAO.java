package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.sdr.api.model.label.LabelArtistResponse;
import ba.com.zira.sdr.api.model.label.LabelSearchRequest;
import ba.com.zira.sdr.api.model.label.LabelSearchResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity_;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.LabelEntity_;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.PersonEntity_;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity_;

@Repository
public class LabelDAO extends AbstractDAO<LabelEntity, Long> {


    public PagedData<LabelSearchResponse> findLabelsByNameFounder(SearchRequest<LabelSearchRequest> request){
        final CriteriaQuery<LabelSearchResponse> criteriaQuery = builder.createQuery(LabelSearchResponse.class);
        final Root<LabelEntity> root = criteriaQuery.from(LabelEntity.class);

        Join<LabelEntity, PersonEntity> founders = root.join(LabelEntity_.founder);
        Join<LabelEntity,SongArtistEntity> songArtists = root.join(LabelEntity_.songArtists);
        Join<SongArtistEntity,ArtistEntity> artists = songArtists.join(SongArtistEntity_.artist);


        List<Predicate> predicates = new ArrayList<>();
        var labelSearchRequest = request.getEntity();

        if (labelSearchRequest.getName() != null && !labelSearchRequest.getName().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get(LabelEntity_.name)), "%" + labelSearchRequest.getName().toLowerCase() + "%"));
        }
        if(labelSearchRequest.getFounders()!=null && !labelSearchRequest.getFounders().isEmpty()) {
            predicates.add(founders.get(PersonEntity_.id).in(labelSearchRequest.getFounders()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        var numberOfArtists = builder.count(artists.get(ArtistEntity_.id));
        var sort = "";
        if (request.getFilter().getSortingFilters() != null && !request.getFilter().getSortingFilters().isEmpty()) {
            sort = request.getFilter().getSortingFilters().get(0).getAttribute();
        }

        Expression<?> modified = builder.selectCase().when(root.get(LabelEntity_.modified).isNull(), root.get(LabelEntity_.created))
                .otherwise(root.get(LabelEntity_.modified));

        if (sort.equals("last_edit")) {
            criteriaQuery.orderBy(builder.desc(modified));

        }

        if (sort.equals("alphabetical")) {
            criteriaQuery.orderBy(builder.asc(root.get(LabelEntity_.name)));

        }
        if (sort.equals("no_of_artists")) {

            criteriaQuery.orderBy(builder.desc(numberOfArtists));

        }

        criteriaQuery.groupBy(root.get(LabelEntity_.id), root.get(LabelEntity_.foundingDate), root.get(LabelEntity_.name),
                root.get(LabelEntity_.information), root.get(LabelEntity_.founder), modified);
        criteriaQuery.multiselect(root.get(LabelEntity_.id), root.get(LabelEntity_.foundingDate), root.get(LabelEntity_.name),
                root.get(LabelEntity_.information), root.get(LabelEntity_.founder).get(PersonEntity_.id), modified,
                numberOfArtists,root.get(LabelEntity_.outlineText));
        return handlePaginationFilterForLabel(request.getFilter(), criteriaQuery);
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

    public List<LabelEntity> findByArtistId(final Long artistId) {
        final CriteriaQuery<LabelEntity> criteriaQuery = builder.createQuery(LabelEntity.class);
        final Root<LabelEntity> root = criteriaQuery.from(LabelEntity.class);
        Join<LabelEntity, SongArtistEntity> songArtists = root.join(LabelEntity_.songArtists);
        Join<SongArtistEntity, ArtistEntity> artists = songArtists.join(SongArtistEntity_.artist);
        criteriaQuery.where(builder.equal(artists.get(ArtistEntity_.id), artistId));

        criteriaQuery.select(root).distinct(true);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public PagedData<LabelSearchResponse> handlePaginationFilterForLabel(final Filter filter,
            final CriteriaQuery<LabelSearchResponse> criteriaQuery) {
        TypedQuery<LabelSearchResponse> query = entityManager.createQuery(criteriaQuery);
        var paginationFilter = filter.getPaginationFilter();
        PagedData<LabelSearchResponse> pagedData = new PagedData<>();
        if (paginationFilter != null && paginationFilter.getPage() >= 0 && paginationFilter.getEntitiesPerPage() > 0) {
            int numberOfRecords = countAllForLabel(criteriaQuery);
            pagedData.setPage(paginationFilter.getPage());
            pagedData.setRecordsPerPage(paginationFilter.getEntitiesPerPage());
            pagedData.setNumberOfPages((int) Math.ceil((float) numberOfRecords / paginationFilter.getEntitiesPerPage()));
            pagedData.setNumberOfRecords(numberOfRecords);
            query.setFirstResult((pagedData.getPage() - 1) * pagedData.getRecordsPerPage());
            query.setMaxResults(pagedData.getRecordsPerPage());
        } else if (paginationFilter != null && paginationFilter.isOffsetLimit()) {
            int numberOfRecords = countAllForLabel(criteriaQuery);
            pagedData.setPage(paginationFilter.getPage());
            pagedData.setRecordsPerPage(paginationFilter.getEntitiesPerPage());
            pagedData.setNumberOfPages((int) Math.ceil((float) numberOfRecords / paginationFilter.getEntitiesPerPage()));

            pagedData.setNumberOfRecords(numberOfRecords);

            query.setFirstResult(paginationFilter.getPage());
            query.setMaxResults(paginationFilter.getEntitiesPerPage());
        }

        pagedData.setRecords(query.getResultList());
        return pagedData;
    }

    private void addJoinsForLabel(final Root<LabelEntity> root, final CriteriaQuery<?> criteriaQuery) {
        for (Root<?> tmpRoot : criteriaQuery.getRoots()) {
            for (Join<?, ?> join : tmpRoot.getJoins()) {
                Join<?, ?> copyOfJoin = root.join(join.getAttribute().getName(), join.getJoinType());
                addJoinsRecursively(join, copyOfJoin);
            }
        }
    }

    private void addJoinsRecursively(final Join<?, ?> parentJoin, final Join<?, ?> copyOfJoin) {
        for (Join<?, ?> join : parentJoin.getJoins()) {
            if (join.getJoins().isEmpty()) {
                copyOfJoin.join(join.getAttribute().getName(), join.getJoinType());
            } else {
                Join<?, ?> copyOfChild = copyOfJoin.join(join.getAttribute().getName(), join.getJoinType());
                addJoinsRecursively(join, copyOfChild);
            }
        }

    }

    public int countAllForLabel(final CriteriaQuery<LabelSearchResponse> criteriaQuery) {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<LabelEntity> root = countQuery.from(LabelEntity.class);
        addJoinsForLabel(root, criteriaQuery);
        countQuery.select(builder.countDistinct(root));
        if (criteriaQuery.getRestriction() != null) {
            countQuery.where(criteriaQuery.getRestriction());
        }
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        return count.intValue();
    }

}
