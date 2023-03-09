package ba.com.zira.sdr.core.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongSimilarityDetailService;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateReq;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailResponse;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongSimilarityDetailMapper;
import ba.com.zira.sdr.core.validation.SongSimilarityDetailRequestValidation;
import ba.com.zira.sdr.dao.SongSimilarityDAO;
import ba.com.zira.sdr.dao.SongSimilarityDetailDAO;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class SongSimilarityDetailServiceImpl implements SongSimilarityDetailService {

    SongSimilarityDetailDAO songSimilarityDetailDAO;
    SongSimilarityDetailRequestValidation songSimilarityDetailRequestValidation;
    SongSimilarityDAO songSimilarityDAO;
    SongSimilarityDetailMapper songSimilarityDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PagedPayloadResponse<SongSimilarityDetail> find(FilterRequest request) throws ApiException {
        PagedData<SongSimilarityDetailEntity> songsimilaritydetailEntities = songSimilarityDetailDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songsimilaritydetailEntities,
                songSimilarityDetailMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetail> create(final EntityRequest<SongSimilarityDetailCreateRequest> request)
            throws ApiException {
        var songSimilarityDetailEntity = songSimilarityDetailMapper.dtoToEntity(request.getEntity());

        songSimilarityDetailEntity.setStatus(Status.ACTIVE.value());
        songSimilarityDetailEntity.setCreated(LocalDateTime.now());
        songSimilarityDetailEntity.setCreatedBy(request.getUserId());

        songSimilarityDetailDAO.persist(songSimilarityDetailEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songSimilarityDetailMapper.entityToDto(songSimilarityDetailEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetail> delete(final EntityRequest<Long> request) throws ApiException {
        songSimilarityDetailRequestValidation.validateExistsSongSimilartyDetailRequest(request);

        var songSimilarityDetailEntity = songSimilarityDetailDAO.findByPK(request.getEntity());
        songSimilarityDetailDAO.remove(songSimilarityDetailEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, songSimilarityDetailMapper.entityToDto(songSimilarityDetailEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetail> update(final EntityRequest<SongSimilarityDetailUpdateRequest> request)
            throws ApiException {
        songSimilarityDetailRequestValidation.validateUpdateSongSimilartyDetailRequest(request);
        var songSimilarityDetailEntity = songSimilarityDetailDAO.findByPK(request.getEntity().getId());

        songSimilarityDetailMapper.updateEntity(request.getEntity(), songSimilarityDetailEntity);

        songSimilarityDetailEntity.setModified(LocalDateTime.now());
        songSimilarityDetailEntity.setModifiedBy(request.getUserId());

        songSimilarityDetailDAO.merge(songSimilarityDetailEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songSimilarityDetailMapper.entityToDto(songSimilarityDetailEntity));

    }

    @Override
    public PagedPayloadResponse<SongSimilarityDetail> get(final FilterRequest filterRequest) {
        PagedData<SongSimilarityDetailEntity> songArtistEntities = songSimilarityDetailDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, songArtistEntities, songSimilarityDetailMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarityDetail> createSongSimilarityDetail(final EntityRequest<SongSimilarityDetailCreateReq> request) {

        SongSimilarityDetailEntity songSimilarityDetailEntity = songSimilarityDetailMapper.dtoToEntity1(request.getEntity());

        songSimilarityDetailEntity.setStatus(Status.ACTIVE.value());
        songSimilarityDetailEntity.setCreated(LocalDateTime.now());
        songSimilarityDetailEntity.setCreatedBy(request.getUserId());

        songSimilarityDetailDAO.persist(songSimilarityDetailEntity);

        SongSimilarityEntity songSimilarityEntity = songSimilarityDAO.findByPK(songSimilarityDetailEntity.getSongSimilarity().getId());

        BigDecimal sumSimilarityScore = BigDecimal.ZERO;
        List<SongSimilarityDetailEntity> songSimilarityDetailEntities = songSimilarityEntity.getSongSimilarityDetails();
        for (SongSimilarityDetailEntity s : songSimilarityDetailEntities) {
            sumSimilarityScore = sumSimilarityScore.add(s.getGrade());
        }
        sumSimilarityScore = sumSimilarityScore.add(songSimilarityDetailEntity.getGrade());

        BigDecimal avgSimilarityScore = sumSimilarityScore.divide(new BigDecimal(songSimilarityDetailEntities.size() + 1), 10,
                RoundingMode.HALF_UP);

        songSimilarityEntity.setTotalSimilarityScore(avgSimilarityScore);

        songSimilarityDAO.merge(songSimilarityEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, songSimilarityDetailMapper.entityToDto(songSimilarityDetailEntity));
    }

    @SuppressWarnings("unused")
    private void updateTotalSimilarityScore(Long songSimilarityId) {
        SongSimilarityEntity songSimilarity = songSimilarityDAO.findByPK(songSimilarityId);
        if (songSimilarity != null) {
            BigDecimal totalSimilarityScore = BigDecimal.ONE;
            for (SongSimilarityDetailEntity detail : songSimilarity.getSongSimilarityDetails()) {
                if (detail.getStatus().equals(Status.ACTIVE.value())) {
                    totalSimilarityScore = totalSimilarityScore.multiply(detail.getGrade());
                }
            }
            songSimilarity.setTotalSimilarityScore(totalSimilarityScore);
            songSimilarityDAO.merge(songSimilarity);
        }
    }

    @Override
    public ListPayloadResponse<SongSimilarityDetailResponse> getAll(final EntityRequest<Long> request) throws ApiException {
        Long songSimilarityId = request.getEntity();
        List<SongSimilarityDetailResponse> songSimilarityDetail = songSimilarityDetailDAO.getAllSongASimilarityDetail(songSimilarityId);
        return new ListPayloadResponse<>(request, ResponseCode.OK, songSimilarityDetail);
    }

}
