package ba.com.zira.sdr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.SpotifyIntegrationEntity;

@Repository
public class SpotifyIntegrationDAO extends AbstractDAO<SpotifyIntegrationEntity, Long> {

    public List<SpotifyIntegrationEntity> getObjectsWithoutSpotifyId(int responseLimit) {

        var albumHql = "select si from SpotifyIntegrationEntity si join AlbumEntity a on si.objectId=a.id and si.objectType like :type"
                + " where a.spotifyId=null and a.spotifyStatus=null";
        var artistHql = "select si from SpotifyIntegrationEntity si join ArtistEntity art on si.objectId=art.id and si.objectType like :type"
                + " where art.spotifyId=null and art.spotifyStatus=null";
        var songHql = "select si from SpotifyIntegrationEntity si join SongEntity s on si.objectId=s.id and si.objectType like :type"
                + " where s.spotifyId=null and s.spotifyStatus=null";

        List<SpotifyIntegrationEntity> list1 = entityManager.createQuery(albumHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "ALBUM").setMaxResults(responseLimit).getResultList();
        List<SpotifyIntegrationEntity> list2 = entityManager.createQuery(artistHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "ARTIST").setMaxResults(responseLimit).getResultList();
        List<SpotifyIntegrationEntity> list3 = entityManager.createQuery(songHql, SpotifyIntegrationEntity.class)
                .setParameter("type", "SONG").setMaxResults(responseLimit).getResultList();
        list1.addAll(list2);
        list1.addAll(list3);
        return list1;
    }

}
