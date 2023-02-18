package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistDeleteRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.core.mapper.ArtistMapper;
import ba.com.zira.sdr.core.validation.ArtistValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArtistImpl implements ArtistService {
    ArtistDAO artistDAO;
    ArtistMapper artistMapper;
    ArtistValidation artistRequestValidation;

    @Override
    public ListPayloadResponse<ArtistResponse> getAll(EmptyRequest req) throws ApiException {

        List<ArtistResponse> multiSearchList = artistDAO.getAllArtists();
        return new ListPayloadResponse<>(req, ResponseCode.OK, multiSearchList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> create(final EntityRequest<ArtistCreateRequest> request) throws ApiException {
        var artistEntity = artistMapper.dtoToEntity(request.getEntity());
        artistEntity.setCreated(LocalDateTime.now());
        artistEntity.setCreatedBy(request.getUserId());
        artistEntity.setModified(LocalDateTime.now());
        artistEntity.setModifiedBy(request.getUserId());

        artistDAO.persist(artistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, artistMapper.entityToDto(artistEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> delete(final EntityRequest<ArtistDeleteRequest> request) throws ApiException {
        artistDAO.removeByPK(request.getEntity().getId());
        return new PayloadResponse<>(request, ResponseCode.OK, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<ArtistResponse> update(final EntityRequest<ArtistUpdateRequest> request) throws ApiException {
        artistRequestValidation.validateUpdateArtistRequest(request);

        var artistEntity = artistDAO.findByPK(request.getEntity().getId());
        artistMapper.updateEntity(request.getEntity(), artistEntity);
        artistEntity.setCreated(LocalDateTime.now());
        artistEntity.setCreatedBy(request.getUserId());
        artistEntity.setModified(LocalDateTime.now());
        artistEntity.setModifiedBy(request.getUserId());

        artistDAO.merge(artistEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, artistMapper.entityToDto(artistEntity));
    }

}
