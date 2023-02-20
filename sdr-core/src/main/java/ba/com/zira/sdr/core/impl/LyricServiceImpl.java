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
import ba.com.zira.sdr.api.LyricService;
import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;
import ba.com.zira.sdr.core.mapper.LyricMapper;
import ba.com.zira.sdr.core.validation.LyricRequestValidation;
import ba.com.zira.sdr.dao.LyricDAO;
import ba.com.zira.sdr.dao.model.LyricEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LyricServiceImpl implements LyricService {

    LyricDAO lyricDAO;
    LyricMapper lyricMapper;
    LyricRequestValidation lyricRequestValidation;

    @Override
    public PagedPayloadResponse<Lyric> find(final FilterRequest request) throws ApiException {
        PagedData<LyricEntity> lyricEntities = lyricDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, lyricEntities, lyricMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Lyric> create(final EntityRequest<LyricCreateRequest> request) throws ApiException {

        var lyricEntity = lyricMapper.dtoToEntity(request.getEntity());
        lyricEntity.setStatus(Status.ACTIVE.value());
        lyricEntity.setCreated(LocalDateTime.now());
        lyricEntity.setCreatedBy(request.getUserId());

        lyricDAO.persist(lyricEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, lyricMapper.entityToDto(lyricEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Lyric> update(final EntityRequest<LyricUpdateRequest> request) throws ApiException {
        lyricRequestValidation.validateUpdateLyricRequest(request);

        var lyricEntity = lyricDAO.findByPK(request.getEntity().getId());
        lyricMapper.updateEntity(request.getEntity(), lyricEntity);

        lyricEntity.setModified(LocalDateTime.now());
        lyricEntity.setModifiedBy(request.getUserId());
        lyricDAO.merge(lyricEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, lyricMapper.entityToDto(lyricEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Lyric> delete(final EntityRequest<Long> request) throws ApiException {
        lyricRequestValidation.validateExistsLyricRequest(request);

        var lyricEntity = lyricDAO.findByPK(request.getEntity());
        lyricDAO.remove(lyricEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, lyricMapper.entityToDto(lyricEntity));
    }

}
