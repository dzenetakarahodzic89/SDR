package ba.com.zira.sdr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.SongArtistEntity;

@Repository
public class SongArtistDAO extends AbstractDAO<SongArtistEntity, Long> {

    public Map<Long, String> songArtistEntityByArtist(Long artistId) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(s.id,s.status) from SongArtistEntity s where s.artist.id = :id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", artistId);
        try {
            return q.getResultStream().collect(Collectors.toMap(LoV::getId, LoV::getName));
        } catch (Exception e) {
            return new HashMap<>();
        }

    }

    public List<SongArtistEntity> songArtistByAlbum(Long albumId) {
        var hql = "select s from SongArtistEntity s where s.album.id = :albumId";
        TypedQuery<SongArtistEntity> q = entityManager.createQuery(hql, SongArtistEntity.class).setParameter("albumId", albumId);
        return q.getResultList();
    }

    public void deleteByAlbumId(Long albumId) {
        var hql = "delete from SongArtistEntity s where s.album.id =:albumId";
        Query q = entityManager.createQuery(hql).setParameter("albumId", albumId);
        q.executeUpdate();
    }

}
