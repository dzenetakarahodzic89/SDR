package ba.com.zira.sdr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.SpotifyIntegrationEntity;

@Repository
public class SpotifyIntegrationDAO extends AbstractDAO<SpotifyIntegrationEntity, Long> {

    public List<SpotifyIntegrationEntity> getObjectsWithoutSpotifyId(int responseLimit) {

        var albumHql = "select si from SpotifyIntegrationEntity si join AlbumEntity a on si.objectId=a.id and si.objectType like :type"
                + " where (a.spotifyId is null or length(a.spotifyId)<1) and (a.spotifyStatus!=:status or a.spotifyStatus is null)";
        var artistHql = "select si from SpotifyIntegrationEntity si join ArtistEntity art on si.objectId=art.id and si.objectType like :type"
                + " where (art.spotifyId is null or length(art.spotifyId)<1) and (art.spotifyStatus!=:status or art.spotifyStatus is null)";
        var songHql = "select si from SpotifyIntegrationEntity si join SongEntity s on si.objectId=s.id and si.objectType like :type"
                + " where (s.spotifyId is null or length(s.spotifyId)<1) and (s.spotifyStatus!=:status or s.spotifyStatus is null)";

        String status = "status";
        String done = "Done";
        List<SpotifyIntegrationEntity> list1 = entityManager.createQuery(albumHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "ALBUM").setParameter(status, done).setMaxResults(responseLimit).getResultList();
        List<SpotifyIntegrationEntity> list2 = entityManager.createQuery(artistHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "ARTIST").setParameter(status, done).setMaxResults(responseLimit).getResultList();
        List<SpotifyIntegrationEntity> list3 = entityManager.createQuery(songHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "SONG").setParameter(status, done).setMaxResults(responseLimit).getResultList();
        list1.addAll(list2);
        list1.addAll(list3);
        if (list1.size() > 15) {
            return list1.subList(0, 15);
        }
        return list1;
    }

    public String getResponseByObjectIdAndObjectType(Long objectId, String objectType) {
        var hql = "select si.response from SpotifyIntegrationEntity si where si.objectId=:objectId and si.objectType like :objectType";
        return entityManager.createQuery(hql, String.class).setParameter("objectType", objectType).setParameter("objectId", objectId)
                .setMaxResults(1).getSingleResult();
    }

}
