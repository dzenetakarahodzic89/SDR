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
import ba.com.zira.sdr.api.SongFftResultService;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResult;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResultCreateRequest;
import ba.com.zira.sdr.api.model.SongFFt.SongFftResultUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongFftMapper;
import ba.com.zira.sdr.core.validation.SongFftResultValidation;
import ba.com.zira.sdr.dao.SongDAO;
//import ba.com.zira.sdr.core.validation.SongFftResultValidation;
import ba.com.zira.sdr.dao.SongFftResultDAO;
import ba.com.zira.sdr.dao.model.SongFttResultEntity;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class SongFftResultImpl implements SongFftResultService {

    SongFftResultDAO songFftResultDAO;
    SongFftMapper songFftMapper;
    SongFftResultValidation songFftResultValidation;
    SongDAO songDAO;
    @Override
    public PagedPayloadResponse<SongFftResult> find(final FilterRequest request) throws ApiException {
        PagedData<SongFttResultEntity> songFttResultEntities = songFftResultDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songFttResultEntities, songFftMapper::entitiesToDtos);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongFftResult> create(final EntityRequest<SongFftResultCreateRequest> request) throws ApiException {
        songFftResultValidation.validateCreateSongFftResultRequest(request);

        var songFttResultEntity = songFftMapper.dtoToEntity(request.getEntity());
        var songEntity=songDAO.findByPK(request.getEntity().getSongID());
        songFttResultEntity.setSong(songEntity);
        songFttResultEntity.setStatus(Status.ACTIVE.value());
        songFttResultEntity.setCreated(LocalDateTime.now());
        songFttResultEntity.setCreatedBy(request.getUserId());

        songFftResultDAO.persist(songFttResultEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songFftMapper.entityToDto(songFttResultEntity));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongFftResult> update(final EntityRequest<SongFftResultUpdateRequest> request) throws ApiException {
        songFftResultValidation.validateUpdateSongFftResultRequest(request);

        var songFttResultEntity = songFftResultDAO.findByPK(request.getEntity().getId());
        songFftMapper.updateEntity(request.getEntity(), songFttResultEntity);
        var songEntity=songDAO.findByPK(request.getEntity().getSongID());
        songFttResultEntity.setSong(songEntity);
        songFttResultEntity.setModified(LocalDateTime.now());
        songFttResultEntity.setModifiedBy(request.getUserId());
        songFftResultDAO.merge(songFttResultEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songFftMapper.entityToDto(songFttResultEntity));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongFftResult> activate(final EntityRequest<Long> request) throws ApiException {
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
    public PayloadResponse<SongFftResult> delete(final EntityRequest<Long> request) throws ApiException {
        songFftResultValidation.validateExistsSongFftResultRequest(request);

        var sampleModelEntity = songFftResultDAO.findByPK(request.getEntity());
        songFftResultDAO.remove(sampleModelEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songFftMapper.entityToDto(sampleModelEntity));
    }
}
