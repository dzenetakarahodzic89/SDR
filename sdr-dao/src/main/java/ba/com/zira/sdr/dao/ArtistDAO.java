package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.artist.ArtistLabelResponse;
import ba.com.zira.sdr.api.artist.ArtistPersonResponse;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistSongResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.PersonArtistEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity;

@Repository
public class ArtistDAO extends AbstractDAO<ArtistEntity, Long> {

    public List<ArtistResponse> getAllArtists(String name) {
        var hql = "select new ba.com.zira.sdr.api.artist.ArtistResponse(r.id, r.name, r.dateOfBirth,"
                + " r.dateOfDeath, r.information, r.status, r.surname, r.type) from ArtistEntity r where r.name like :name";
        TypedQuery<ArtistResponse> query = entityManager.createQuery(hql, ArtistResponse.class).setParameter("name", '%' + name + '%');
        return query.getResultList();
    }

    public List<ArtistResponse> getAllArtists() {
        var hql = "select new ba.com.zira.sdr.api.artist.ArtistResponse(r.id, r.name, r.dateOfBirth,"
                + " r.dateOfDeath, r.information, r.status, r.surname, r.type) from ArtistEntity r";
        TypedQuery<ArtistResponse> query = entityManager.createQuery(hql, ArtistResponse.class);
        return query.getResultList();
    }

    public List<ArtistSongResponse> getBySongId(Long songId) {
        var hql = "select distinct new ba.com.zira.sdr.api.artist.ArtistSongResponse(sa.id,sa.name || ' ' || sa.surname,sp.id,sp.name || ' ' || sp.surname,sp.dateOfBirth,ssi.name, sa2.name,sl.name) from SongArtistEntity ssa join ArtistEntity sa on ssa.artist.id = sa.id join PersonArtistEntity spa on sa.id =spa.artist.id join PersonEntity sp on spa.person.id = sp.id left join SongInstrumentEntity ssi on ssi.person.id =sp.id join AlbumEntity sa2 on ssa.album.id = sa2.id join LabelEntity sl on ssa.label.id = sl.id  where ssa.song.id = :id";
        TypedQuery<ArtistSongResponse> q = entityManager.createQuery(hql, ArtistSongResponse.class).setParameter("id", songId);
        return q.getResultList();
    }

    public List<ArtistPersonResponse> getArtistByPersonId(Long personId) {

        var hql = "select new ba.com.zira.sdr.api.artist.ArtistPersonResponse(sa.id, sa.name, sa.created, sa.createdBy, sa.dateOfBirth, sa.dateOfDeath, sa.information, sa.modified, sa.modifiedBy, sa.status, sa.surname, sa.type) from PersonEntity sp inner join PersonArtistEntity spa on sp.id = spa.person.id  "
                + "inner join ArtistEntity sa on spa.artist.id = sa.id where sp.id = :id";
        TypedQuery<ArtistPersonResponse> q = entityManager.createQuery(hql, ArtistPersonResponse.class).setParameter("id", personId);
        return q.getResultList();
    }

    public List<ArtistLabelResponse> getId(Long labelId) {
        var hql = "select distinct new ba.com.zira.sdr.api.artist.ArtistLabelResponse(sa.id,sa.name || ' ' || sa.surname,sp.name || ' ' || sp.surname,sp.dateOfBirth,sa2.name) from SongArtistEntity ssa join ArtistEntity sa on ssa.artist.id = sa.id join PersonArtistEntity spa on sa.id =spa.artist.id join PersonEntity sp on spa.person.id = sp.id  join AlbumEntity sa2 on ssa.album.id = sa2.id where ssa.label.id = :id";
        TypedQuery<ArtistLabelResponse> q = entityManager.createQuery(hql, ArtistLabelResponse.class).setParameter("id", labelId);
        return q.getResultList();
    }

    public Boolean songArtistExist(Long id) {
        var hql = "select s from SongArtistEntity s where s.artist.id = :id";
        TypedQuery<SongArtistEntity> q = entityManager.createQuery(hql, SongArtistEntity.class).setParameter("id", id);
        try {

            q.getSingleResult();
            return true;
        } catch (NonUniqueResultException e) {
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public Boolean personArtistExist(Long id) {
        var hql = "select s from PersonArtistEntity s where s.artist.id = :id";
        TypedQuery<PersonArtistEntity> q = entityManager.createQuery(hql, PersonArtistEntity.class).setParameter("id", id);
        try {
            q.getSingleResult();
            return true;
        } catch (NonUniqueResultException e) {
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public List<LoV> artistsByEras(Long eraId) {
        var hql = "select distinct new ba.com.zira.sdr.api.model.lov.LoV(a.id,a.name) from EraEntity e join AlbumEntity al on e.id=al.era.id "
                + "join SongArtistEntity sa on al.id=sa.album.id join ArtistEntity a on sa.artist.id=a.id where e.id=:id";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class).setParameter("id", eraId);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<LoV> getArtistLoVs() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(a.id,a.name || ' ' || a.surname) from ArtistEntity a";
        TypedQuery<LoV> q = entityManager.createQuery(hql, LoV.class);
        return q.getResultList();
    }

    public List<LoV> findArtistsToFetchFromSpotify(int responseLimit) {
        var cases = "case when a.surname is null then concat('artist:',a.name) else" + " concat('artist:',a.name,' ',a.surname) end";
        var hql = "select distinct new ba.com.zira.sdr.api.model.lov.LoV(a.id," + cases + ") from ArtistEntity a left join"
                + " SpotifyIntegrationEntity si on a.id = si.objectId and si.objectType like :artist where si.id = null";
        return entityManager.createQuery(hql, LoV.class).setParameter("artist", "ARTIST").setMaxResults(responseLimit).getResultList();

    }

    public List<ArtistEntity> findArtistsToFetchAlbumsFromSpotify(int responseLimit) {
        var hql = "select a from ArtistEntity a where a.spotifyId is not null and length(a.spotifyId)>0 and a.spotifyStatus!=:status";
        return entityManager.createQuery(hql, ArtistEntity.class).setMaxResults(responseLimit).setParameter("status", "Done")
                .getResultList();
    }

    public List<ArtistEntity> artistsByAlbum(Long albumId) {
        var hql = "select art from ArtistEntity art join SongArtistEntity sa on art.id=sa.artist.id where sa.album.id=:albumId";
        return entityManager.createQuery(hql, ArtistEntity.class).setParameter("albumId", albumId).getResultList();
    }

    public Long countSoloArtistsByEras(Long era) {
        var hql = "select count(distinct sa.artist.id) from EraEntity e join AlbumEntity al on e.id=al.era.id "
                + "join SongArtistEntity sa on al.id=sa.album.id join ArtistEntity a on sa.artist.id=a.id where e.id=:id "
                + "and (select count(pa.person.id) from PersonArtistEntity pa where pa.artist.id=a.id) = 1";
        TypedQuery<Long> q = entityManager.createQuery(hql, Long.class).setParameter("id", era);
        return q.getSingleResult();
    }

    public Long countGroupArtistsByEras(Long era) {
        var hql = "select count(distinct sa.artist.id) from EraEntity e join AlbumEntity al on e.id=al.era.id "
                + "join SongArtistEntity sa on al.id=sa.album.id join ArtistEntity a on sa.artist.id=a.id where e.id=:id "
                + "and (select count(pa.person.id) from PersonArtistEntity pa where pa.artist.id=a.id) > 1";
        TypedQuery<Long> q = entityManager.createQuery(hql, Long.class).setParameter("id", era);
        return q.getSingleResult();
    }

}
