package ba.com.zira.sdr.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.playlist.Playlist;
import ba.com.zira.sdr.dao.model.PlaylistEntity;

@Repository
public class PlaylistDAO extends AbstractDAO<PlaylistEntity, Long> {

    public List<Playlist> getAllPlaylists(String name) {
        String hql = "select new ba.com.zira.sdr.api.model.playlist.Playlist(p.id, p.name, p.information, p.status) "
                + "from PlaylistEntity p where p.name like :name";
        TypedQuery<Playlist> query = entityManager.createQuery(hql, Playlist.class).setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public List<Playlist> getAllPlaylists() {
        String hql = "select new ba.com.zira.sdr.api.model.playlist.Playlist(p.id, p.name, p.information, p.status) "
                + "from PlaylistEntity p";
        TypedQuery<Playlist> query = entityManager.createQuery(hql, Playlist.class);
        return query.getResultList();
    }

}
