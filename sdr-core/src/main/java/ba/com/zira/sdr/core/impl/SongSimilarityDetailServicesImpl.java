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
import ba.com.zira.sdr.api.SongSimilarityDetailService;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModel;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelCreateRequest;
import ba.com.zira.sdr.core.mapper.SongSimilarityDetailModelMapper;
import ba.com.zira.sdr.core.validation.SongSimilarityDetailRequestValidation;
import ba.com.zira.sdr.dao.SongSimilarityDetailDAO;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    GenreDAO genreDAO;
    GenreModelMapper genreModelMapper;
    GenreRequestValidation genreRequestValidation;

    @Override
    public PagedPayloadResponse<GenreModel> find(final FilterRequest request) throws ApiException {
        PagedData<GenreEntity> genreEntities = genreDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, genreEntities, genreModelMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<GenreModel> create(final EntityRequest<GenreModelCreateRequest> request) throws ApiException {
        genreRequestValidation.validateGenreModelCreateRequest(request);

        var genreEntity = genreModelMapper.dtoToEntity(request.getEntity());

        if (request.getEntity().getMainGenreID() != null) {
            var mainGenreEntity = genreDAO.findByPK(request.getEntity().getMainGenreID());
            genreEntity.setMainGenre(mainGenreEntity);
        }

        genreEntity.setStatus(Status.ACTIVE.value());
        genreEntity.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        genreEntity.setCreatedBy(request.getUserId());

        genreDAO.persist(genreEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, genreModelMapper.entityToDto(genreEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<GenreModel> update(final EntityRequest<GenreModelUpdateRequest> request) throws ApiException {
        genreRequestValidation.validateGenreModelUpdateRequest(request);

        var genreEntity = genreDAO.findByPK(request.getEntity().getId());
        System.out.println(genreEntity.getName());
        System.out.println(genreEntity.getType());
        genreModelMapper.updateEntity(request.getEntity(), genreEntity);

        System.out.println(request.getEntity().toString());
        System.out.println(genreEntity.getName());
        System.out.println(genreEntity.getType());
        if (request.getEntity().getMainGenreID() != null) {
            var mainGenreEntity = genreDAO.findByPK(request.getEntity().getMainGenreID());
            genreEntity.setMainGenre(mainGenreEntity);
        }

        genreEntity.setModified(Timestamp.valueOf(LocalDateTime.now()));
        genreEntity.setModifiedBy(request.getUserId());
        genreDAO.merge(genreEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, genreModelMapper.entityToDto(genreEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<GenreModel> activate(final EntityRequest<Long> request) throws ApiException {
        genreRequestValidation.validateExistsGenreModelRequest(request);

        var genreEntity = genreDAO.findByPK(request.getEntity());
        genreEntity.setStatus(Status.ACTIVE.value());
        genreEntity.setModified(Timestamp.valueOf(LocalDateTime.now()));
        genreEntity.setModifiedBy(request.getUser().getUserId());
        genreDAO.merge(genreEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, genreModelMapper.entityToDto(genreEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<GenreModel> delete(final EntityRequest<Long> request) throws ApiException {
        genreRequestValidation.validateExistsGenreModelRequest(request);

        var genreEntity = genreDAO.findByPK(request.getEntity());
        genreDAO.remove(genreEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, genreModelMapper.entityToDto(genreEntity));

    }

}
