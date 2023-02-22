package ba.com.zira.sdr.core.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.GenreService;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.genre.GenreCreateRequest;
import ba.com.zira.sdr.api.model.genre.GenreUpdateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.GenreMapper;
import ba.com.zira.sdr.core.validation.GenreRequestValidation;
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

}
