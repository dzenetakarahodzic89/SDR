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
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ArtistService;
import ba.com.zira.sdr.api.artist.ArtistCreateRequest;
import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.artist.ArtistUpdateRequest;
import ba.com.zira.sdr.api.utils.PagedDataMetadataMapper;
import ba.com.zira.sdr.core.mapper.ArtistMapper;
import ba.com.zira.sdr.core.validation.ArtistValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.PersonArtistDAO;
import ba.com.zira.sdr.dao.SongArtistDAO;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    ArtistDAO artistDAO;
    PersonArtistDAO personArtistDAO;
    SongArtistDAO songArtistDAO;
    ArtistMapper artistMapper;
    ArtistValidation artistRequestValidation;

    /*
     * @Override public ListPayloadResponse<ArtistResponse> getAll(EmptyRequest
     * req) throws ApiException {
     *
     * List<ArtistResponse> multiSearchList = artistDAO.getAllArtists(); return
     * new ListPayloadResponse<>(req, ResponseCode.OK, multiSearchList); }
     */

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
    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException {
        artistRequestValidation.validateExistsArtistRequest(request);
        Long id = request.getEntity();

        if (artistDAO.personArtistExist(id)) {
            ValidationErrors errors = new ValidationErrors();
            errors.put(ValidationError.of("PERSON_ARTIST_EXISTS", "Not allowed to be deleted."));
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, "Artist delete validation error");
        }

        if (artistDAO.songArtistExist(id)) {
            ValidationErrors errors = new ValidationErrors();
            errors.put(ValidationError.of("SONG_ARTIST_EXISTS", "Not allowed to be deleted."));
            return new PayloadResponse<>(request, ResponseCode.REQUEST_INVALID, "Artist delete validation error");
        }

        artistDAO.remove(artistDAO.findByPK(id));
        return new PayloadResponse<>(request, ResponseCode.OK, "Artist successfully deleted!");
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

    @Override
    public PagedPayloadResponse<ArtistResponse> find(final FilterRequest request) throws ApiException {
        PagedData<ArtistEntity> artistEntity = artistDAO.findAll(request.getFilter());
        PagedData<ArtistResponse> artists = new PagedData<ArtistResponse>();
        artists.setRecords(artistMapper.entitiesToDtos(artistEntity.getRecords()));
        PagedDataMetadataMapper.remapMetadata(artistEntity, artists);
        artists.getRecords().forEach(artist -> {
            artist.setSongArtistNames(songArtistDAO.songArtistEntityByArtist(artist.getId()));
            artist.setPersonArtistNames(personArtistDAO.personArtistEntityByArtist(artist.getId()));

        });
        return new PagedPayloadResponse<>(request, ResponseCode.OK, artists);
    }
}
