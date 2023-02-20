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
import ba.com.zira.sdr.api.SongArtistService;
import ba.com.zira.sdr.api.model.songartist.SongArtistCreateRequest;
import ba.com.zira.sdr.api.model.songartist.SongArtistResponse;
import ba.com.zira.sdr.core.mapper.SongArtistMapper;
import ba.com.zira.sdr.core.validation.SongArtistRequestValidation;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongArtistServiceImpl implements SongArtistService {
    SongArtistDAO songArtistDAO;
    SongArtistMapper songArtistMapper;
    SongArtistRequestValidation songArtistRequestValidation;

    @Override
    public PagedPayloadResponse<SongArtistResponse> get(final FilterRequest filterRequest) throws ApiException {
        PagedData<SongArtistEntity> songArtistEntities = songArtistDAO.findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, songArtistEntities, songArtistMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongArtistResponse> create(final EntityRequest<SongArtistCreateRequest> entityRequest) throws ApiException {
        songArtistRequestValidation.validateCreateSongArtistRequest(entityRequest);

        SongArtistEntity songArtistEntity = songArtistMapper.dtoToEntity(entityRequest.getEntity());

        songArtistEntity.setStatus(Status.ACTIVE.value());

        songArtistEntity.setCreated(LocalDateTime.now());
        songArtistEntity.setCreatedBy(entityRequest.getUserId());

        songArtistEntity = songArtistDAO.persist(songArtistEntity);

        return new PayloadResponse<>(entityRequest, ResponseCode.OK, songArtistMapper.entityToDto(songArtistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SongArtistResponse> delete(final EntityRequest<Long> entityRequest) {
        songArtistRequestValidation.validateDeleteSongArtistRequest(entityRequest);

        SongArtistEntity deletedEntity = songArtistDAO.findByPK(entityRequest.getEntity());

        songArtistDAO.removeByPK(entityRequest.getEntity());
        return new PayloadResponse<>(entityRequest, ResponseCode.OK, songArtistMapper.entityToDto(deletedEntity));
    }
}
