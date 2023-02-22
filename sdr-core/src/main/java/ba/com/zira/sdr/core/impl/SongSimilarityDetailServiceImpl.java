package ba.com.zira.sdr.core.impl;

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
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailModelUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongSimilarityDetailModelMapper;
import ba.com.zira.sdr.core.validation.SongSimilarityDetailRequestValidation;
import ba.com.zira.sdr.dao.SongSimilarityDetailDAO;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class SongSimilarityDetailServiceImpl implements SongSimilarityDetailService {

    SongSimilarityDetailDAO songsimilaritydetailDAO;
    SongSimilarityDetailModelMapper songsimilaritydetailModelMapper;
    SongSimilarityDetailRequestValidation songsimilaritydetailRequestValidation;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PagedPayloadResponse<SongSimilarityDetailModel> find(FilterRequest request) throws ApiException {
        PagedData<SongSimilarityDetailEntity> songsimilaritydetailEntities = songsimilaritydetailDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailEntities,
                songsimilaritydetailModelMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetailModel> delete(final EntityRequest<Long> request) throws ApiException {
        songsimilaritydetailRequestValidation.validateExistsSongSimilartyDetailRequest(request);

        var SongSimilarityDetailEntity = songsimilaritydetailDAO.findByPK(request.getEntity());
        songsimilaritydetailDAO.remove(SongSimilarityDetailEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailModelMapper.entityToDto(SongSimilarityDetailEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetailModel> create(final EntityRequest<SongSimilarityDetailModelCreateRequest> request)
            throws ApiException {
        var songSimilarityDetailEntity = songsimilaritydetailModelMapper.dtoToEntity(request.getEntity());

        songSimilarityDetailEntity.setStatus(Status.ACTIVE.value());
        songSimilarityDetailEntity.setCreated(LocalDateTime.now());
        songSimilarityDetailEntity.setCreatedBy(request.getUserId());

        songsimilaritydetailDAO.persist(songSimilarityDetailEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailModelMapper.entityToDto(songSimilarityDetailEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetailModel> update(final EntityRequest<SongSimilarityDetailModelUpdateRequest> request)
            throws ApiException {
        songsimilaritydetailRequestValidation.validateUpdateSongSimilartyDetailRequest(request);
        var songSimilarityDetailEntity = songsimilaritydetailDAO.findByPK(request.getEntity().getId());

        songsimilaritydetailModelMapper.updateEntity(request.getEntity(), songSimilarityDetailEntity);

        songSimilarityDetailEntity.setModified(LocalDateTime.now());
        songSimilarityDetailEntity.setModifiedBy(request.getUserId());
        // songSimilarityDetailEntity.merge(songSimilarityDetailEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailModelMapper.entityToDto(songSimilarityDetailEntity));
    }

}
