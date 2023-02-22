package ba.com.zira.sdr.core.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.GenreService;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreEraOverview;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.api.model.genre.SongGenreEraLink;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.GenreMapper;
import ba.com.zira.sdr.core.validation.GenreRequestValidation;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.GenreEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    GenreDAO genreDAO;
    GenreMapper genreMapper;
    GenreRequestValidation genreRequestValidation;
    SongDAO songDAO;
    EraDAO eraDAO;

    @Override
    public PagedPayloadResponse<Genre> find(final FilterRequest request) throws ApiException {
        PagedData<GenreEntity> genreEntities = genreDAO.findAll(request.getFilter());
        PagedData<Genre> genres = new PagedData<Genre>();
        genres.setRecords(genreMapper.entitiesToDtos(genreEntities.getRecords()));
        PagedDataMetadataMapper.remapMetadata(genreEntities, genres);
        genres.getRecords().forEach(genre -> {
            genre.setSongNames(songDAO.songsByGenre(genre.getId()));
            genre.setSubGenreNames(genreDAO.subGenresByMainGenre(genre.getId()));
        });
        return new PagedPayloadResponse<>(request, ResponseCode.OK, genres);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Genre> create(final EntityRequest<GenreCreateRequest> request) throws ApiException {
        genreRequestValidation.validateGenreCreateRequest(request);

        var genreEntity = genreMapper.dtoToEntity(request.getEntity());

        if (request.getEntity().getMainGenreId() != null) {
            var mainGenreEntity = genreDAO.findByPK(request.getEntity().getMainGenreId());
            genreEntity.setMainGenre(mainGenreEntity);
        }

        genreEntity.setStatus(Status.ACTIVE.value());
        genreEntity.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        genreEntity.setCreatedBy(request.getUserId());

        genreDAO.persist(genreEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, genreMapper.entityToDto(genreEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Genre> update(final EntityRequest<GenreUpdateRequest> request) throws ApiException {
        genreRequestValidation.validateGenreUpdateRequest(request);

        var genreEntity = genreDAO.findByPK(request.getEntity().getId());
        genreMapper.updateEntity(request.getEntity(), genreEntity);

        if (request.getEntity().getMainGenreId() != null) {
            var mainGenreEntity = genreDAO.findByPK(request.getEntity().getMainGenreId());
            genreEntity.setMainGenre(mainGenreEntity);
        }

        genreEntity.setModified(Timestamp.valueOf(LocalDateTime.now()));
        genreEntity.setModifiedBy(request.getUserId());
        genreDAO.merge(genreEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, genreMapper.entityToDto(genreEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException {
        genreRequestValidation.validateGenreDeleteRequest(request);

        genreDAO.removeByPK(request.getEntity());

        return new PayloadResponse<>(request, ResponseCode.OK, "Genre successfully deleted!");

    }

    @Override
    public ListPayloadResponse<GenreEraOverview> getGenresOverEras(EmptyRequest request) throws ApiException {
        List<SongGenreEraLink> links = songDAO.findSongGenreEraLinks();

        System.out.println(links);

        Map<LoV, List<SongGenreEraLink>> songsByEras = new HashMap<>();

        links.stream().forEach(link -> {
            var eraLoV = new LoV(link.getEraId(), link.getEraName());
            var songLinks = songsByEras.get(eraLoV);

            if (songLinks == null) {
                songLinks = new LinkedList<>();
                songLinks.add(link);
            } else {
                songLinks.add(link);
            }

            songsByEras.put(eraLoV, songLinks);
        });

        List<GenreEraOverview> genresOverEras = new LinkedList<>();

        songsByEras.forEach((era, songLinks) -> {
            Map<LoV, Double> genrePercentageInEra = calculateGenrePercentageInEra(songLinks);
            var genreEraOverview = new GenreEraOverview(era.getId(), era.getName(), genrePercentageInEra);
            genresOverEras.add(genreEraOverview);
        });

        /*
         * List<GenreEraOverview> genresOverEras = new LinkedList<>(); var
         * eraEntities = eraDAO.findAll(); eraEntities.forEach(era -> { Map<LoV,
         * Double> genrePercentageInEra = new HashMap<>(); List<SongEntity>
         * songsInEra = new LinkedList<>(); var albums = era.getAlbums();
         * albums.forEach(album -> { var songArtists = album.getSongArtists();
         * songArtists.forEach(songArtist -> { var song = songArtist.getSong();
         * songsInEra.add(song); var genre = song.getGenre(); while
         * (genre.getMainGenre() != null) { genre = genre.getMainGenre(); } var
         * genreLoV = new LoV(genre.getId(), genre.getName()); Double
         * numberOfSongs = 1.0; if (genrePercentageInEra.containsKey(genreLoV))
         * { numberOfSongs += genrePercentageInEra.get(genreLoV); }
         *
         * genrePercentageInEra.put(genreLoV, numberOfSongs);
         *
         * }); }); genrePercentageInEra.replaceAll((key, value) -> (value /
         * songsInEra.size()) * 100); var genreEraOverview = new
         * GenreEraOverview(era.getId(), era.getName(), genrePercentageInEra);
         * genresOverEras.add(genreEraOverview); });
         */

        return new ListPayloadResponse<>(request, ResponseCode.OK, genresOverEras);
    }

    private Map<LoV, Double> calculateGenrePercentageInEra(List<SongGenreEraLink> songLinks) {
        Map<LoV, Double> genrePercentageInEra = new HashMap<>();
        songLinks.forEach(link -> {
            var genreLoV = new LoV(link.getGenreId(), link.getGenreName());
            Integer numberOfSongs = 1;
            if (genrePercentageInEra.containsKey(genreLoV)) {
                numberOfSongs += genrePercentageInEra.get(genreLoV).intValue();
            }
            genrePercentageInEra.put(genreLoV, numberOfSongs.doubleValue());
        });
        genrePercentageInEra.replaceAll((key, value) -> (value / songLinks.size()) * 100);
        return genrePercentageInEra;
    }

}
