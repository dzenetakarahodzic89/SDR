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
import ba.com.zira.sdr.core.mapper.GenreMapper;
import ba.com.zira.sdr.core.validation.GenreRequestValidation;
import ba.com.zira.sdr.dao.GenreDAO;
import ba.com.zira.sdr.dao.model.GenreEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    GenreDAO genreDAO;
    GenreMapper genreMapper;
    GenreRequestValidation genreRequestValidation;

    @Override
    public PagedPayloadResponse<Genre> find(final FilterRequest request) throws ApiException {
        PagedData<GenreEntity> genreEntities = genreDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, genreEntities, genreMapper::entitiesToDtos);
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
    public PayloadResponse<Genre> delete(final EntityRequest<Long> request) throws ApiException {
        genreRequestValidation.validateGenreDeleteRequest(request);

        var genreEntity = genreDAO.findByPK(request.getEntity());
        genreDAO.remove(genreEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, genreMapper.entityToDto(genreEntity));

    }

}
