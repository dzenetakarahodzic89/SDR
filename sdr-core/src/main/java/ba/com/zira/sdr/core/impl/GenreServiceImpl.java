package ba.com.zira.sdr.core.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
import ba.com.zira.commons.message.request.SearchRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.GenreService;
import ba.com.zira.sdr.api.model.genre.EraRequest;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreEraOverview;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.api.model.genre.GenresEraPercentageResponse;
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

    @Override
    public ListPayloadResponse<GenreEraOverview> getGenresOverEras(SearchRequest<EraRequest> request) throws ApiException {
        List<SongGenreEraLink> links = songDAO.findSongGenreEraLinks(request);

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
            Map<String, Double> genrePercentageInEra = new HashMap<>();
            songLinks.forEach(link -> {
                var genreLoV = new LoV(link.getGenreId(), link.getGenreName());
                Integer numberOfSongs = 1;
                if (genrePercentageInEra.containsKey(genreLoV)) {
                    numberOfSongs += genrePercentageInEra.get(genreLoV).intValue();
                }
                genrePercentageInEra.put(genreLoV.getName(), numberOfSongs.doubleValue());
            });
            genrePercentageInEra.replaceAll((key, value) -> (value / songLinks.size()) * 100);
            Collection<Double> values = genrePercentageInEra.values();
            List<Double> list = new ArrayList<>(values);
            ArrayList<String> keyList = new ArrayList<String>(genrePercentageInEra.keySet());
            List<GenresEraPercentageResponse> genreList = new LinkedList<>();
            for (int i = 0; i < genrePercentageInEra.size(); i++) {
                var response = new GenresEraPercentageResponse(list.get(i), keyList.get(i));
                genreList.add(response);
            }

            var genreEraOverview = new GenreEraOverview(era.getId(), era.getName(), genreList, keyList, list);
            genresOverEras.add(genreEraOverview);
        });
        return new ListPayloadResponse<>(request, ResponseCode.OK, genresOverEras);
    }

    /**
     * Gets the sub genre main genre names.
     *
     * @param request
     *            the request
     * @return the sub genre main genre names
     * @throws ApiException
     *             the api exception
     */
    @Override
    public ListPayloadResponse<LoV> getSubGenreMainGenreNames(final EmptyRequest request) throws ApiException {
        var subGenreMainGenreNames = genreDAO.getSubGenreMainGenreNames();
        return new ListPayloadResponse<>(request, ResponseCode.OK, subGenreMainGenreNames);
    }

    @Override
    public ListPayloadResponse<LoV> getMainGenreLoV(final EmptyRequest request) throws ApiException {
        List<LoV> mainGenres = genreDAO.getMainGenreLoV();
        return new ListPayloadResponse<>(request, ResponseCode.OK, mainGenres);
    }

    @Override
    public ListPayloadResponse<LoV> getSubgenreLoV(final EntityRequest<Long> request) throws ApiException {
        List<LoV> subgenres = genreDAO.getSubgenreLoV(request.getEntity());
        return new ListPayloadResponse<>(request, ResponseCode.OK, subgenres);

    }

    @Override
    public ListPayloadResponse<LoV> getGenreLoVs(EmptyRequest request) throws ApiException {
        var genres = genreDAO.getGenreLoV();
        return new ListPayloadResponse<>(request, ResponseCode.OK, genres);
    }

}
