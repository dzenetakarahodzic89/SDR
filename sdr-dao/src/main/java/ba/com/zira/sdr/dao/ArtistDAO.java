package ba.com.zira.sdr.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.artist.ArtistResponse;
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

    public List<ArtistResponse> getAllArtists1() {
        String hql = "select a from ArtistEntity a left join a.personArtists pa left join a.songArtists sa";

        TypedQuery<ArtistEntity> query = entityManager.createQuery(hql, ArtistEntity.class);

        List<ArtistEntity> artistEntities = query.getResultList();
        List<ArtistResponse> artistResponses = new ArrayList<>();

        for (ArtistEntity artistEntity : artistEntities) {
            List<LoV> personArtists = new ArrayList<>();
            for (PersonArtistEntity personArtistEntity : artistEntity.getPersonArtists()) {
                personArtists.add(new LoV(personArtistEntity.getId(), personArtistEntity.getStatus()));
            }

            List<LoV> songArtists = new ArrayList<>();
            for (SongArtistEntity songArtistEntity : artistEntity.getSongArtists()) {
                songArtists.add(new LoV(songArtistEntity.getId(), songArtistEntity.getStatus()));
            }

            ArtistResponse artistResponse = new ArtistResponse(artistEntity.getId(), artistEntity.getName(), artistEntity.getCreated(),
                    artistEntity.getCreatedBy(), artistEntity.getDateOfBirth(), artistEntity.getDateOfDeath(),
                    artistEntity.getInformation(), artistEntity.getModified(), artistEntity.getModifiedBy(), artistEntity.getStatus(),
                    artistEntity.getSurname(), artistEntity.getType(), personArtists, songArtists);
            artistResponses.add(artistResponse);
        }

        return artistResponses;
    }

}
