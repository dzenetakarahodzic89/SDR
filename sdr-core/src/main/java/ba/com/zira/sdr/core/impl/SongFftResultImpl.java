package ba.com.zira.sdr.core.impl;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongFftResultService;
import ba.com.zira.sdr.api.model.song.fft.SongFftResult;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.song.fft.SongFftResultUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongFftMapper;
import ba.com.zira.sdr.core.validation.SongFftResultValidation;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongFftResultDAO;
import ba.com.zira.sdr.dao.model.SongFttResultEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SongFftResultImpl implements SongFftResultService {

    SongFftResultDAO songFftResultDAO;
    SongFftMapper songFftMapper;
    SongFftResultValidation songFftResultValidation;
    SongDAO songDAO;

    @Override
    public PagedPayloadResponse<SongFftResult> find(final FilterRequest request) {
        PagedData<SongFttResultEntity> songFttResultEntities = songFftResultDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songFttResultEntities, songFftMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongFftResult> create(final EntityRequest<SongFftResultCreateRequest> request) {
        songFftResultValidation.validateCreateSongFftResultRequest(request);

        var songFttResultEntity = songFftMapper.dtoToEntity(request.getEntity());
        var songEntity = songDAO.findByPK(request.getEntity().getSongId());
        songFttResultEntity.setSong(songEntity);
        songFttResultEntity.setStatus(Status.ACTIVE.value());
        songFttResultEntity.setCreated(LocalDateTime.now());
        songFttResultEntity.setCreatedBy(request.getUserId());

        songFftResultDAO.persist(songFttResultEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songFftMapper.entityToDto(songFttResultEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongFftResult> update(final EntityRequest<SongFftResultUpdateRequest> request) {
        songFftResultValidation.validateUpdateSongFftResultRequest(request);

        var songFttResultEntity = songFftResultDAO.findByPK(request.getEntity().getId());
        songFftMapper.updateEntity(request.getEntity(), songFttResultEntity);
        var songEntity = songDAO.findByPK(request.getEntity().getSongID());
        songFttResultEntity.setSong(songEntity);
        songFttResultEntity.setModified(LocalDateTime.now());
        songFttResultEntity.setModifiedBy(request.getUserId());
        songFftResultDAO.merge(songFttResultEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songFftMapper.entityToDto(songFttResultEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongFftResult> activate(final EntityRequest<Long> request) {
        songFftResultValidation.validateExistsSongFftResultRequest(request);

        var songFttResultEntity = songFftResultDAO.findByPK(request.getEntity());
        songFttResultEntity.setStatus(Status.ACTIVE.value());
        songFttResultEntity.setModified(LocalDateTime.now());
        songFttResultEntity.setModifiedBy(request.getUser().getUserId());
        songFftResultDAO.merge(songFttResultEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songFftMapper.entityToDto(songFttResultEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongFftResult> delete(final EntityRequest<Long> request) {
        songFftResultValidation.validateExistsSongFftResultRequest(request);

        var sampleModelEntity = songFftResultDAO.findByPK(request.getEntity());
        songFftResultDAO.remove(sampleModelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songFftMapper.entityToDto(sampleModelEntity));
    }
}
