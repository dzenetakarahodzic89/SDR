package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SongInstrumentService;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrument;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentCreateRequest;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentUpdateRequest;
import ba.com.zira.sdr.core.mapper.SongInstrumentMapper;
import ba.com.zira.sdr.core.validation.SongInstrumentValidation;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongInstrumentServiceImpl implements SongInstrumentService {

    SongInstrumentDAO songInstrumentDAO;
    SongInstrumentMapper songInstrumentMapper;
    SongInstrumentValidation songInstrumentValidation;

    @Override
    public PagedPayloadResponse<SongInstrument> find(final FilterRequest request) {
        PagedData<SongInstrumentEntity> songInstrumentEntities = songInstrumentDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, songInstrumentEntities, songInstrumentMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongInstrument> create(final EntityRequest<SongInstrumentCreateRequest> request) {
        var songInstrumentEntity = songInstrumentMapper.dtoToEntity(request.getEntity());
        songInstrumentEntity.setCreated(LocalDateTime.now());
        songInstrumentEntity.setCreatedBy(request.getUserId());
        songInstrumentEntity.setModified(LocalDateTime.now());
        songInstrumentEntity.setModifiedBy(request.getUserId());
        songInstrumentDAO.persist(songInstrumentEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songInstrumentMapper.entityToDto(songInstrumentEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongInstrument> update(final EntityRequest<SongInstrumentUpdateRequest> request) {
        songInstrumentValidation.validateUpdateSongInstrumentRequest(request);

        var songInstrumentEntity = songInstrumentDAO.findByPK(request.getEntity().getId());
        songInstrumentMapper.updateEntity(request.getEntity(), songInstrumentEntity);

        songInstrumentEntity.setModified(LocalDateTime.now());
        songInstrumentEntity.setModifiedBy(request.getUserId());
        songInstrumentDAO.merge(songInstrumentEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, songInstrumentMapper.entityToDto(songInstrumentEntity));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        songInstrumentValidation.validateExistsSongInstrumentRequest(request);
        songInstrumentDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, "Record removed successfully!");
    }
}
