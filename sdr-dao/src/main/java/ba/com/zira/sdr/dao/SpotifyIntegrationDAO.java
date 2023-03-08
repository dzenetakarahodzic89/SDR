package ba.com.zira.sdr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.SpotifyIntegrationEntity;

@Repository
public class SpotifyIntegrationDAO extends AbstractDAO<SpotifyIntegrationEntity, Long> {

    public List<SpotifyIntegrationEntity> getObjectsWithoutSpotifyId(int responseLimit) {

        var albumHql = "select si from SpotifyIntegrationEntity si join AlbumEntity a on si.objectId=a.id and si.objectType like :type"
                + " where a.spotifyId is null and a.spotifyStatus!=:status";
        var artistHql = "select si from SpotifyIntegrationEntity si join ArtistEntity art on si.objectId=art.id and si.objectType like :type"
                + " where art.spotifyId is null and art.spotifyStatus!=:status";
        var songHql = "select si from SpotifyIntegrationEntity si join SongEntity s on si.objectId=s.id and si.objectType like :type"
                + " where s.spotifyId is null and s.spotifyStatus!=:status";

        List<SpotifyIntegrationEntity> list1 = entityManager.createQuery(albumHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "ALBUM").setParameter("status", "Done").setMaxResults(responseLimit).getResultList();
        List<SpotifyIntegrationEntity> list2 = entityManager.createQuery(artistHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "ARTIST").setParameter("status", "Done").setMaxResults(responseLimit).getResultList();
        List<SpotifyIntegrationEntity> list3 = entityManager.createQuery(songHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "SONG").setParameter("status", "Done").setMaxResults(responseLimit).getResultList();
        list1.addAll(list2);
        list1.addAll(list3);
        if (list1.size() > 5) {
            return list1.subList(0, 5);
        }
        return list1;
    }

}
