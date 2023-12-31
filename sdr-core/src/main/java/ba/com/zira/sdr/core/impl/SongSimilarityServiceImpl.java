package ba.com.zira.sdr.core.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongSimilarityService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.songsimilarity.SongSimilarity;
import ba.com.zira.sdr.api.model.songsimilarity.SongSimilarityCreateRequest;
import ba.com.zira.sdr.api.model.songsimilarity.SongSimilarityResponse;
import ba.com.zira.sdr.core.mapper.SongSimilarityMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.SongSimilarityRequestValidation;
import ba.com.zira.sdr.dao.SongSimilarityDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class SongSimilarityServiceImpl implements SongSimilarityService {

    SongSimilarityDAO songSimilarityDAO;
    SongSimilarityMapper songSimilarityMapper;
    SongSimilarityRequestValidation songsimilarityRequestValidation;
    LookupService lookupService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongSimilarity> create(final EntityRequest<SongSimilarityCreateRequest> request) {
        songsimilarityRequestValidation.validateSongSimilarityRequest(request);

        var songSimilarityEntity = songSimilarityMapper.dtoToEntity(request.getEntity());

        songSimilarityEntity.setStatus(Status.ACTIVE.value());
        songSimilarityEntity.setCreated(LocalDateTime.now());
        songSimilarityEntity.setCreatedBy(request.getUserId());
        songSimilarityEntity.setTotalSimilarityScore(BigDecimal.valueOf(0));

        songSimilarityDAO.persist(songSimilarityEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songSimilarityMapper.entityToDto(songSimilarityEntity));
    }

    @Override
    public ListPayloadResponse<SongSimilarityResponse> getAll(EmptyRequest req) throws ApiException {
        List<SongSimilarityResponse> songSimilarity = songSimilarityDAO.getAllSongSimilarity();
        lookupService.lookupCoverImage(songSimilarity, SongSimilarityResponse::getSongAId, ObjectType.SONG.getValue(),
                SongSimilarityResponse::setSongAimageUrl, SongSimilarityResponse::getSongAimageUrl);
        lookupService.lookupCoverImage(songSimilarity, SongSimilarityResponse::getSongBId, ObjectType.SONG.getValue(),
                SongSimilarityResponse::setSongBimageUrl, SongSimilarityResponse::getSongBimageUrl);
        lookupService.lookupAudio(songSimilarity, SongSimilarityResponse::getSongAId, SongSimilarityResponse::setSongAAudioUrl);
        lookupService.lookupAudio(songSimilarity, SongSimilarityResponse::getSongBId, SongSimilarityResponse::setSongBAudioUrl);
        return new ListPayloadResponse<>(req, ResponseCode.OK, songSimilarity);
    }

    @Override
    public PayloadResponse<SongSimilarityResponse> getOne(EmptyRequest req) throws ApiException {
        SongSimilarityResponse songSimilarity = songSimilarityDAO.getRandomSongSimilarity();

        lookupService.lookupCoverImage(Arrays.asList(songSimilarity), SongSimilarityResponse::getSongAId, ObjectType.SONG.getValue(),
                SongSimilarityResponse::setSongAimageUrl, SongSimilarityResponse::getSongAimageUrl);
        lookupService.lookupCoverImage(Arrays.asList(songSimilarity), SongSimilarityResponse::getSongBId, ObjectType.SONG.getValue(),
                SongSimilarityResponse::setSongBimageUrl, SongSimilarityResponse::getSongBimageUrl);
        lookupService.lookupAudio((Arrays.asList(songSimilarity)), SongSimilarityResponse::getSongAId,
                SongSimilarityResponse::setSongAAudioUrl);
        lookupService.lookupAudio((Arrays.asList(songSimilarity)), SongSimilarityResponse::getSongBId,
                SongSimilarityResponse::setSongBAudioUrl);
        return new PayloadResponse<>(req, ResponseCode.OK, songSimilarity);
    }

}
