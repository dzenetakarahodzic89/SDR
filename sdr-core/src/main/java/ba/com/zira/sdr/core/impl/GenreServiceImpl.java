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

/**
 * The Class GenreServiceImpl.
 */
@Service

/**
 * Instantiates a new genre service impl.
 *
 * @param genreDAO
 *            the genre DAO
 * @param genreMapper
 *            the genre mapper
 * @param genreRequestValidation
 *            the genre request validation
 * @param songDAO
 *            the song DAO
 * @param eraDAO
 *            the era DAO
 */
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    /** The genre DAO. */
    GenreDAO genreDAO;

    /** The genre mapper. */
    GenreMapper genreMapper;

    /** The genre request validation. */
    GenreRequestValidation genreRequestValidation;

    /** The song DAO. */
    SongDAO songDAO;

    /** The era DAO. */
    EraDAO eraDAO;

    /**
     * Find genres.
     *
     * @param request
     *            the request
     * @return the paged payload response
     */
    @Override
    public PagedPayloadResponse<Genre> find(final FilterRequest request) {
        PagedData<GenreEntity> genreEntities = genreDAO.findAll(request.getFilter());
        PagedData<Genre> genres = new PagedData<>();
        genres.setRecords(genreMapper.entitiesToDtos(genreEntities.getRecords()));
        PagedDataMetadataMapper.remapMetadata(genreEntities, genres);
        genres.getRecords().forEach(genre -> {
            genre.setSongNames(songDAO.songsByGenre(genre.getId()));
            genre.setSubGenreNames(genreDAO.subGenresByMainGenre(genre.getId()));
        });
        return new PagedPayloadResponse<>(request, ResponseCode.OK, genres);
    }

    /**
     * Creates the genre.
     *
     * @param request
     *            the request
     * @return the payload response
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Genre> create(final EntityRequest<GenreCreateRequest> request) {
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

    /**
     * Update genre.
     *
     * @param request
     *            the request
     * @return the payload response
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Genre> update(final EntityRequest<GenreUpdateRequest> request) {
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

    /**
     * Delete genre.
     *
     * @param request
     *            the request
     * @return the payload response
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        genreRequestValidation.validateGenreDeleteRequest(request);

        genreDAO.removeByPK(request.getEntity());

        return new PayloadResponse<>(request, ResponseCode.OK, "Genre successfully deleted!");

    }

    /**
     * Gets the genres over eras.
     *
     * @param request
     *            the request
     * @return the genres over eras
     * @throws ApiException
     *             the api exception
     */
    @Override
    public ListPayloadResponse<GenreEraOverview> getGenresOverEras(final EmptyRequest request) throws ApiException {
        List<SongGenreEraLink> links = songDAO.findSongGenreEraLinks();
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

        return new ListPayloadResponse<>(request, ResponseCode.OK, genresOverEras);
    }

    /**
     * Calculate genre percentage in era.
     *
     * @param songLinks
     *            the song links
     * @return the map
     */
    private Map<LoV, Double> calculateGenrePercentageInEra(final List<SongGenreEraLink> songLinks) {
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

    @Override
    public ListPayloadResponse<LoV> getMainGenresLoV(final EmptyRequest request) throws ApiException {
        List<LoV> mainGenres = genreDAO.getMainGenresLoV();
        return new ListPayloadResponse<>(request, ResponseCode.OK, mainGenres);
    }

    @Override
    public ListPayloadResponse<LoV> getSubgenresLoV(final EntityRequest<Long> request) throws ApiException {
        List<LoV> subgenres = genreDAO.getSubgenresLoV(request.getEntity());
        return new ListPayloadResponse<>(request, ResponseCode.OK, subgenres);

    }

}
