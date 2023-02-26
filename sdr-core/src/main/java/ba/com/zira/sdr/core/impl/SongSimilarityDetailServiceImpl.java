package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
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
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongSimilarityDetailMapper;
import ba.com.zira.sdr.core.validation.SongSimilarityDetailRequestValidation;
import ba.com.zira.sdr.dao.SongSimilarityDetailDAO;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class SongSimilarityDetailServiceImpl implements SongSimilarityDetailService {

    SongSimilarityDetailDAO songsimilaritydetailDAO;
    SongSimilarityDetailMapper songsimilaritydetailModelMapper;
    SongSimilarityDetailRequestValidation songsimilaritydetailRequestValidation;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PagedPayloadResponse<SongSimilarityDetail> find(FilterRequest request) throws ApiException {
        PagedData<SongSimilarityDetailEntity> songsimilaritydetailEntities = songsimilaritydetailDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailEntities,
                songsimilaritydetailModelMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetail> delete(final EntityRequest<Long> request) throws ApiException {
        songsimilaritydetailRequestValidation.validateExistsSongSimilartyDetailRequest(request);

        var SongSimilarityDetailEntity = songsimilaritydetailDAO.findByPK(request.getEntity());
        songsimilaritydetailDAO.remove(SongSimilarityDetailEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailModelMapper.entityToDto(SongSimilarityDetailEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetail> create(final EntityRequest<SongSimilarityDetailCreateRequest> request)
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
    public PayloadResponse<SongSimilarityDetail> update(final EntityRequest<SongSimilarityDetailUpdateRequest> request)
            throws ApiException {
        songsimilaritydetailRequestValidation.validateUpdateSongSimilartyDetailRequest(request);
        var songSimilarityDetailEntity = songsimilaritydetailDAO.findByPK(request.getEntity().getId());

        songsimilaritydetailModelMapper.updateEntity(request.getEntity(), songSimilarityDetailEntity);

        songSimilarityDetailEntity.setModified(LocalDateTime.now());
        songSimilarityDetailEntity.setModifiedBy(request.getUserId());
        // songSimilarityDetailEntity.merge(songSimilarityDetailEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailModelMapper.entityToDto(songSimilarityDetailEntity));
    }


    @Override
    public PagedPayloadResponse<SongSimilarityDetail> get(final FilterRequest filterRequest) {
        PagedData<SongSimilarityDetailEntity> songArtistEntities = songsimilaritydetailDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, songArtistEntities, songsimilaritydetailModelMapper::entitiesToDtos);
    }



}
