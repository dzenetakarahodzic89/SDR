package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.model.Filter;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.sdr.api.model.release.ReleaseSearchRequest;
import ba.com.zira.sdr.api.model.release.ReleaseSearchResponse;
import ba.com.zira.sdr.dao.model.CountryEntity;
import ba.com.zira.sdr.dao.model.CountryEntity_;
import ba.com.zira.sdr.dao.model.ReleaseCountryEntity;
import ba.com.zira.sdr.dao.model.ReleaseCountryEntity_;
import ba.com.zira.sdr.dao.model.ReleaseEntity;
import ba.com.zira.sdr.dao.model.ReleaseEntity_;

@Repository
public class ReleaseDAO extends AbstractDAO<ReleaseEntity, Long> {

    private static final Logger LOGGER = Logger.getLogger(ReleaseDAO.class.getName());

    public PagedData<ReleaseSearchResponse> find(final SearchRequest<ReleaseSearchRequest> request) {

        CriteriaQuery<ReleaseSearchResponse> criteriaQuery = builder.createQuery(ReleaseSearchResponse.class);

        Root<ReleaseCountryEntity> root = criteriaQuery.from(ReleaseCountryEntity.class);

        Join<ReleaseCountryEntity, ReleaseEntity> releaseCountries = root.join(ReleaseCountryEntity_.release);
        Join<ReleaseCountryEntity, CountryEntity> countries = root.join(ReleaseCountryEntity_.country);

        List<Predicate> predicates = new ArrayList<>();

        var releaseSearchRequest = request.getEntity();

        if (releaseSearchRequest.getName() != null && !releaseSearchRequest.getName().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get(ReleaseCountryEntity_.release).get(ReleaseEntity_.name)),
                    "%" + releaseSearchRequest.getName().toLowerCase() + "%"));
        }

        if (releaseSearchRequest.getCountryIds() != null && !releaseSearchRequest.getCountryIds().isEmpty()) {
            predicates.add(root.get(ReleaseCountryEntity_.country).get(CountryEntity_.id).in(releaseSearchRequest.getCountryIds()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var sort = "";

        if (request.getFilter().getSortingFilters() != null && !request.getFilter().getSortingFilters().isEmpty()) {
            sort = request.getFilter().getSortingFilters().get(0).getAttribute();
        } else {
            sort = "name asc";
        }

        if (sort.equals("name asc")) {
            criteriaQuery.orderBy(builder.asc(root.get(ReleaseCountryEntity_.release).get(ReleaseEntity_.name)));
        } else if (sort.equals("name desc")) {
            criteriaQuery.orderBy(builder.desc(root.get(ReleaseCountryEntity_.release).get(ReleaseEntity_.name)));
        }

        if (sort.equals("country asc")) {
            criteriaQuery.orderBy(builder.asc(root.get(ReleaseCountryEntity_.country).get(CountryEntity_.name)));
        } else if (sort.equals("country desc")) {
            criteriaQuery.orderBy(builder.desc(root.get(ReleaseCountryEntity_.country).get(CountryEntity_.name)));
        }

        criteriaQuery.multiselect(root.get(ReleaseCountryEntity_.id), root.get(ReleaseCountryEntity_.release).get(ReleaseEntity_.name),
                root.get(ReleaseCountryEntity_.country).get(CountryEntity_.name)).distinct(true);

        return handlePaginationFilterForRelease(request.getFilter(), criteriaQuery);

    }

    public PagedData<ReleaseSearchResponse> handlePaginationFilterForRelease(final Filter filter,
            final CriteriaQuery<ReleaseSearchResponse> criteriaQuery) {

        TypedQuery<ReleaseSearchResponse> query = entityManager.createQuery(criteriaQuery);
        var paginationFilter = filter.getPaginationFilter();
        PagedData<ReleaseSearchResponse> pagedData = new PagedData<>();

        if (paginationFilter != null && paginationFilter.getPage() >= 0 && paginationFilter.getEntitiesPerPage() > 0) {

            int numberOfRecords = countAllForRelease(criteriaQuery);
            LOGGER.info("numberOfRecords in first if: " + numberOfRecords);

            pagedData.setPage(paginationFilter.getPage());
            pagedData.setRecordsPerPage(paginationFilter.getEntitiesPerPage());
            pagedData.setNumberOfPages((int) Math.ceil((float) numberOfRecords / paginationFilter.getEntitiesPerPage()));
            pagedData.setNumberOfRecords(numberOfRecords);

            query.setFirstResult((pagedData.getPage() - 1) * pagedData.getRecordsPerPage());
            query.setMaxResults(pagedData.getRecordsPerPage());

        } else if (paginationFilter != null && paginationFilter.isOffsetLimit()) {

            int numberOfRecords = countAllForRelease(criteriaQuery);
            LOGGER.info("numberOfRecords in second if: " + numberOfRecords);

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

    public int countAllForRelease(final CriteriaQuery<ReleaseSearchResponse> criteriaQuery) {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<ReleaseCountryEntity> root = countQuery.from(ReleaseCountryEntity.class);

        addJoinsForRelease(root, criteriaQuery);
        countQuery.select(builder.countDistinct(root));

        if (criteriaQuery.getRestriction() != null) {
            countQuery.where(criteriaQuery.getRestriction());
        }

        Long count = entityManager.createQuery(countQuery).getSingleResult();
        return count.intValue();
    }

    private void addJoinsForRelease(final Root<ReleaseCountryEntity> root, final CriteriaQuery<?> criteriaQuery) {
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

}
