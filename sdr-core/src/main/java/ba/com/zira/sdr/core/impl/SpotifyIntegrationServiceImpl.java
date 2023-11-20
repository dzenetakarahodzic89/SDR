package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.SpotifyIntegrationService;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationStatistics;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationTypes;
import ba.com.zira.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.zira.sdr.core.mapper.SpotifyIntegrationMapper;
import ba.com.zira.sdr.core.validation.SpotifyIntegrationRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SpotifyIntegrationDAO;
import ba.com.zira.sdr.dao.model.SpotifyIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpotifyIntegrationServiceImpl implements SpotifyIntegrationService {

    SpotifyIntegrationDAO spotifyIntegrationDAO;
    SpotifyIntegrationMapper spotifyIntegrationMapper;
    SpotifyIntegrationRequestValidation spotifyIntegrationRequestValidation;
    SongDAO songDAO;
    ArtistDAO artistDAO;
    AlbumDAO albumDAO;

    @Override
    public PagedPayloadResponse<SpotifyIntegrationResponse> find(final FilterRequest request) {
        PagedData<SpotifyIntegrationEntity> spotifyIntegrationEntities = spotifyIntegrationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationEntities, spotifyIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SpotifyIntegrationResponse> create(final EntityRequest<SpotifyIntegrationCreateRequest> request) {
        spotifyIntegrationRequestValidation.validateCreateSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationMapper.dtoToEntity(request.getEntity());
        spotifyIntegrationEntity.setCreated(LocalDateTime.now());
        spotifyIntegrationEntity.setCreatedBy(request.getUserId());
        spotifyIntegrationEntity.setStatus(Status.ACTIVE.value());

        spotifyIntegrationDAO.persist(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationMapper.entityToDto(spotifyIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SpotifyIntegrationResponse> update(final EntityRequest<SpotifyIntegrationUpdateRequest> request) {
        spotifyIntegrationRequestValidation.validateUpdateSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationDAO.findByPK(request.getEntity().getId());
        spotifyIntegrationMapper.updateEntity(request.getEntity(), spotifyIntegrationEntity);

        spotifyIntegrationEntity.setModified(LocalDateTime.now());
        spotifyIntegrationEntity.setModifiedBy(request.getUserId());
        spotifyIntegrationDAO.merge(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationMapper.entityToDto(spotifyIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        spotifyIntegrationRequestValidation.validateExistsSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationDAO.findByPK(request.getEntity());
        spotifyIntegrationDAO.remove(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "Integration removed successfully!");
    }

    @Override
    public PayloadResponse<SpotifyIntegrationStatistics> getDataForStatistics(EmptyRequest request) {
        var countSongTotal = songDAO.countAll();
        var countSongSpotify = songDAO.countAllSpotifyFields();

        var countArtistTotal = artistDAO.countAll();
        var countArtistSpotify = artistDAO.countAllSpotifyFields();

        var countAlbumTotal = albumDAO.countAll();
        var countAlbumSpotify = albumDAO.countAllSpotifyFields();

        var songType = new SpotifyIntegrationTypes();
        songType.setTableName("SONG");
        songType.setIsFinished(countSongTotal == countSongSpotify);
        songType.setLastModified(songDAO.getLastModifiedSpotifyField());
        songType.setSequence(1L);

        var artistType = new SpotifyIntegrationTypes();
        artistType.setTableName("ARTIST");
        artistType.setIsFinished(countArtistTotal == countArtistSpotify);
        artistType.setLastModified(artistDAO.getLastModifiedSpotifyField());
        artistType.setSequence(2L);

        var albumType = new SpotifyIntegrationTypes();
        albumType.setTableName("ALBUM");
        albumType.setIsFinished(countAlbumTotal == countAlbumSpotify);
        albumType.setLastModified(albumDAO.getLastModifiedSpotifyField());
        albumType.setSequence(3L);

        var spotifyTypes = new ArrayList<SpotifyIntegrationTypes>();

        spotifyTypes.add(songType);
        spotifyTypes.add(artistType);
        spotifyTypes.add(albumType);

        var lastSpotifyDoneFields = songDAO.getLastUpdatedSpotifyFields();

        SpotifyIntegrationStatistics statistics = new SpotifyIntegrationStatistics((long) countSongTotal, (long) countSongSpotify,
                (long) countArtistTotal, (long) countArtistSpotify, (long) countAlbumTotal, (long) countAlbumSpotify, spotifyTypes,
                lastSpotifyDoneFields);
        return new PayloadResponse<>(request, ResponseCode.OK, statistics);
    }

}
