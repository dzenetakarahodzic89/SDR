package ba.com.zira.sdr.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.model.country.CountryArtistSongResponse;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.model.song.SongResponse;
import ba.com.zira.sdr.dao.model.CountryEntity;

@Repository
public class CountryDAO extends AbstractDAO<CountryEntity, Long> {

    public Map<Long, String> getFlagAbbs(final List<Long> ids) {
        var hql = new StringBuilder(
                "select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.flagAbbriviation) from CountryEntity m where m.id in :ids");
        TypedQuery<LoV> query = entityManager.createQuery(hql.toString(), LoV.class).setParameter("ids", ids);
        return query.getResultList().stream().collect(Collectors.toMap(LoV::getId, LoV::getName));
    }

    public List<CountryResponse> getAllCountries() {
        var hql = "select new ba.com.zira.sdr.api.model.country.CountryResponse(r.id, r.name, r.flagAbbriviation, r.region) from CountryEntity r order by r.name asc";
        TypedQuery<CountryResponse> query = entityManager.createQuery(hql, CountryResponse.class);
        return query.getResultList();
    }

    public List<LoV> getAllCountriesExceptOneWithTheSelectedId(final Long id) {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(m.id, m.name) from CountryEntity m where m.id!= :id  order by m.name asc";
        var q = entityManager.createQuery(hql, LoV.class).setParameter("id", id);
        return q.getResultList();
    }

    public List<LoV> getAllCountriesArtistsSongs() {
        var hql = "select new ba.com.zira.sdr.api.model.lov.LoV(c.id, c.name) " + "from CountryEntity c "
                + "join PersonEntity p on c.id = p.country.id " + "join PersonArtistEntity pa on p.id = pa.person.id "
                + "join ArtistEntity a on pa.artist.id = a.id " + "join SongArtistEntity sa on a.id = sa.artist.id "
                + "join SongEntity s on s.id = sa.song.id " + "group by c.id, c.name order by c.name asc";
        TypedQuery<LoV> query = entityManager.createQuery(hql, LoV.class);
        return query.getResultList();
    }

    public CountryArtistSongResponse getArtistsAndSongs(Long countryId) {
        var hql = "select new ba.com.zira.sdr.api.model.country.CountryArtistSongResponse(c.id, c.name, count(distinct a.name), count(distinct s.name)) "
                + "from CountryEntity c " + "join PersonEntity p on c.id = p.country.id "
                + "join PersonArtistEntity pa on p.id = pa.person.id " + "join ArtistEntity a on pa.artist.id = a.id "
                + "join SongArtistEntity sa on a.id = sa.artist.id " + "join SongEntity s on s.id = sa.song.id " + "where c.id = :id "
                + "group by c.id, c.name";
        TypedQuery<CountryArtistSongResponse> query = entityManager.createQuery(hql, CountryArtistSongResponse.class).setParameter("id",
                countryId);
        return query.getSingleResult();
    }

    public List<ArtistResponse> randomArtists(Long countryId, Long teamSize, Long songSize) {
        var hql = "select new ba.com.zira.sdr.api.artist.ArtistResponse(a.id, a.name || ' ' || a.surname) " + "from CountryEntity c "
                + "join PersonEntity p on c.id = p.country.id " + "join PersonArtistEntity pa on p.id = pa.person.id "
                + "join ArtistEntity a on pa.artist.id = a.id " + "join SongArtistEntity ssa on ssa.artist.id = a.id "
                + "join SongEntity s on ssa.song.id = s.id " + "where c.id in( " + "select  c.id " + "from CountryEntity c "
                + "join PersonEntity p on c.id = p.country.id " + "join PersonArtistEntity pa on p.id = pa.person.id "
                + "join ArtistEntity a2 on pa.artist.id = a2.id join SongArtistEntity ssa ON ssa.artist.id = a2.id "
                + "join SongEntity s on ssa.song.id = s.id  " + "where c.id = :countryId " + "group by c.id, c.name "
                + "having count(distinct a2.id) >= :teamSize) " + "and c.id = :countryId " + "group by a.id, c.id, c.name "
                + "having count(distinct s.id) >= :songSize " + "order by random()";

        TypedQuery<ArtistResponse> query = entityManager.createQuery(hql, ArtistResponse.class).setParameter("countryId", countryId)
                .setParameter("songSize", songSize).setParameter("teamSize", teamSize).setMaxResults(teamSize.intValue());
        return query.getResultList();
    }

    public List<SongResponse> randomSongs(Long artistId, Long songSize) {
        var hql = "select new ba.com.zira.sdr.api.model.song.SongResponse (s.id, s.name, s.spotifyId) " + "from SongEntity s "
                + "join SongArtistEntity sa on s.id = sa.song.id " + "join ArtistEntity a on sa.artist.id = a.id "
                + "where a.id = :artistId " + "order by random()";
        TypedQuery<SongResponse> query = entityManager.createQuery(hql, SongResponse.class).setParameter("artistId", artistId)
                .setMaxResults(songSize.intValue());
        return query.getResultList();
    }

}
