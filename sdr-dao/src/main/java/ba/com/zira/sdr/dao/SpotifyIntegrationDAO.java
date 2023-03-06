package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.SpotifyIntegrationEntity;

@Repository
public class SpotifyIntegrationDAO extends AbstractDAO<SpotifyIntegrationEntity, Long> {

    public List<SpotifyIntegrationEntity> getObjectsWithoutSpotifyId(int responseLimit) {

        var hql = "select si from SpotifyIntegrationEntity si join AlbumEntity a on si.objectId=a.id and si.objectType like 'ALBUM'"
                + " join ArtistEntity art on si.objectId=art.id and si.objectType like 'ARTIST'"
                + " join SongEntity s on si.objectId and si.objectType like 'SONG'"
                + " where a.spotifyId=null and s.spotifyId=null and art.spotifyId=null";

        TypedQuery<SpotifyIntegrationEntity> q = entityManager.createQuery(hql, SpotifyIntegrationEntity.class)
                .setMaxResults(responseLimit);
        return q.getResultList();
    }

}
