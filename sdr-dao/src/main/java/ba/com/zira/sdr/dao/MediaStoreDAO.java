package ba.com.zira.sdr.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.media.CoverImageHelper;
import ba.com.zira.sdr.dao.model.MediaStoreEntity;

@Repository
public class MediaStoreDAO extends AbstractDAO<MediaStoreEntity, String> {
    public Map<Long, List<CoverImageHelper>> getUrlsForList(final List<Long> objectIds, final String objectType, final String type) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(
                "SELECT new ba.com.zira.sdr.api.model.media.CoverImageHelper(me.objectId, me.objectType,mse.url) FROM MediaStoreEntity mse, MediaEntity me WHERE me.id = mse.media.id AND me.objectId IN :objectIds AND me.objectType = :objectType AND mse.type = :type");

        TypedQuery<CoverImageHelper> query = entityManager.createQuery(stringBuilder.toString(), CoverImageHelper.class);
        query.setParameter("objectIds", objectIds);
        query.setParameter("objectType", objectType);
        query.setParameter("type", type);

        return query.getResultList().stream().collect(Collectors.groupingBy(CoverImageHelper::getObjectId));

    }

}
