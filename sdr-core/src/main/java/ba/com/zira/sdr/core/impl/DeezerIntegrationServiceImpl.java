package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.User;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.DeezerIntegrationService;
import ba.com.zira.sdr.api.enums.DeezerStatus;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationStatisticsResponse;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationTypes;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.mapper.DeezerIntegrationMapper;
import ba.com.zira.sdr.core.utils.HTMLEncoder;
import ba.com.zira.sdr.core.validation.DeezerIntegrationRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.DeezerIntegrationDAO;
import ba.com.zira.sdr.dao.MediaDAO;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;
import ba.com.zira.sdr.dao.model.MediaEntity;
import ba.com.zira.sdr.dao.model.MediaStoreEntity;
import ba.com.zira.sdr.deezer.ArtistSearch;
import ba.com.zira.sdr.deezer.tracklist.Contributor;
import ba.com.zira.sdr.deezer.tracklist.Tracklist;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeezerIntegrationServiceImpl implements DeezerIntegrationService {

    DeezerIntegrationDAO deezerIntegrationDAO;
    DeezerIntegrationMapper deezerIntegrationMapper;
    DeezerIntegrationRequestValidation deezerIntegrationRequestValidation;
    SongDAO songDAO;
    ArtistDAO artistDAO;
    ObjectMapper objectMapper;
    MediaDAO mediaDAO;
    MediaStoreDAO mediaStoreDAO;
    AlbumDAO albumDAO;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeezerIntegrationServiceImpl.class);
    private static final User systemUser = new User("Deezer Integration");

    @Override
    public PagedPayloadResponse<DeezerIntegration> find(final FilterRequest request) throws ApiException {
        PagedData<DeezerIntegrationEntity> deezerIntegrationEntities = deezerIntegrationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, deezerIntegrationEntities, deezerIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<DeezerIntegration> create(final EntityRequest<DeezerIntegrationCreateRequest> request) throws ApiException {
        var deezerIntegrationEntity = deezerIntegrationMapper.dtoToEntity(request.getEntity());

        deezerIntegrationEntity.setId(UUID.randomUUID().toString());
        deezerIntegrationEntity.setStatus(Status.ACTIVE.value());
        deezerIntegrationEntity.setCreated(LocalDateTime.now());
        deezerIntegrationEntity.setCreatedBy(request.getUserId());

        deezerIntegrationDAO.persist(deezerIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, deezerIntegrationMapper.entityToDto(deezerIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<DeezerIntegration> update(final EntityRequest<DeezerIntegrationUpdateRequest> request) {
        deezerIntegrationRequestValidation.validateUpdateDeezerIntegrationRequest(request);

        var deezerIntegrationEntity = deezerIntegrationDAO.findByPK(request.getEntity().getId());
        deezerIntegrationMapper.updateEntity(request.getEntity(), deezerIntegrationEntity);

        deezerIntegrationEntity.setModified(LocalDateTime.now());
        deezerIntegrationEntity.setModifiedBy(request.getUserId());
        deezerIntegrationDAO.merge(deezerIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, deezerIntegrationMapper.entityToDto(deezerIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<String> request) throws ApiException {
        deezerIntegrationRequestValidation.validateExistsDeezerIntegrationRequest(request);

        var deezerIntegrationEntity = deezerIntegrationDAO.findByPK(request.getEntity());
        deezerIntegrationDAO.remove(deezerIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "Deezer integration is removed.");
    }

    private static final String ARTIST_CONST = "ARTIST";
    private static final String COVER_IMAGE_CONST = "COVER_IMAGE";
    private static final Boolean INTEGRATION_DISABLED_CONST = true;

    @Scheduled(initialDelay = 100, fixedDelay = 300000L)
    public void getArtistInformationFromDeezer() {
        if (Boolean.TRUE.equals(INTEGRATION_DISABLED_CONST)) {
            LOGGER.info("Deezer integration disabled!");
            return;
        }
        List<LoV> artistsForSearch = artistDAO.getArtistsForDeezerSearch();
        for (var artist : artistsForSearch) {
            var artistDeezerInformation = new DeezerIntegrationEntity();
            artistDeezerInformation.setId(UUID.randomUUID().toString());
            artistDeezerInformation.setCreatedBy(systemUser.getUserId());
            artistDeezerInformation.setCreated(LocalDateTime.now());
            artistDeezerInformation.setObjectType(ARTIST_CONST);
            artistDeezerInformation.setStatus(Status.ACTIVE.getValue());
            artistDeezerInformation.setObjectId(artist.getId());
            var url = "https://api.deezer.com/search/artist?q=" + artist.getName();
            artistDeezerInformation.setRequest(url);
            artistDeezerInformation.setName("artist:" + artist.getName());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                artistDeezerInformation.setResponse(response.getBody());
                deezerIntegrationDAO.persist(artistDeezerInformation);
            } else {
                LOGGER.error(response.getBody());
            }
        }
        if (artistsForSearch.isEmpty()) {
            LOGGER.info("All artists have been searched for.");
        }
    }

    @Scheduled(initialDelay = 100, fixedDelay = 300000L)
    @Transactional
    public void insertDataIntoTables() throws JsonProcessingException {
        if (Boolean.TRUE.equals(INTEGRATION_DISABLED_CONST)) {
            LOGGER.info("Deezer integration disabled!");
            return;
        }
        fillSongDataUsingTracklist();
        var artistApiResponses = deezerIntegrationDAO.getForTableFill("Active", ARTIST_CONST);
        if (artistApiResponses.isEmpty()) {
            LOGGER.info("There is no artist responses to go through.");
            return;
        }
        for (var artistApiResponse : artistApiResponses) {
            var responseData = objectMapper.readValue(artistApiResponse.getResponse(), ArtistSearch.class);
            for (var entity : responseData.getData()) {

                if (entity.getName().equalsIgnoreCase(artistApiResponse.getName().substring(7))) {
                    artistDAO.updateDeezerFields(artistApiResponse.getObjectId(), (long) entity.getId(), (long) entity.getNbFan());
                    getTrackListInformationFromDeezer(entity.getTracklist(), artistApiResponse.getName().substring(7),
                            artistApiResponse.getObjectId());
                }
                if (entity.getName().contains(artistApiResponse.getName().substring(7))) {
                    var newMediaStore = new MediaStoreEntity();
                    newMediaStore.setId(UUID.randomUUID().toString());
                    newMediaStore.setName(artistApiResponse.getName().substring(7));
                    newMediaStore.setType(COVER_IMAGE_CONST);
                    var media = mediaDAO.findByTypeAndId(ARTIST_CONST, artistApiResponse.getObjectId());
                    if (media == null) {
                        var newMedia = new MediaEntity();
                        newMedia.setObjectType(ARTIST_CONST);
                        newMedia.setObjectId(artistApiResponse.getObjectId());
                        newMedia.setCreated(LocalDateTime.now());
                        newMedia.setCreatedBy(systemUser.getUserId());
                        newMediaStore.setMedia(mediaDAO.persist(newMedia));
                    } else {
                        newMediaStore.setMedia(media);
                    }
                    newMediaStore.setCreated(LocalDateTime.now());
                    newMediaStore.setCreatedBy(systemUser.getUserId());
                    if (!entity.getPictureXl().isEmpty()) {
                        newMediaStore.setUrl(entity.getPictureXl());
                        newMediaStore.setExtension(entity.getPictureXl().substring(entity.getPictureXl().lastIndexOf(".") + 1));
                    } else if (!entity.getPicture().isEmpty()) {
                        newMediaStore.setUrl(entity.getPicture());
                        newMediaStore.setExtension(entity.getPicture().substring(entity.getPicture().lastIndexOf(".") + 1));
                    }
                    mediaStoreDAO.persist(newMediaStore);
                }
            }
            deezerIntegrationDAO.updateStatus(DeezerStatus.SAVED.getValue(), artistApiResponse.getId());
        }
    }

    private void getTrackListInformationFromDeezer(String tracklistUrl, String artistFullName, Long artistId) {
        var trackListDeezerInformation = new DeezerIntegrationEntity();
        trackListDeezerInformation.setId(UUID.randomUUID().toString());
        trackListDeezerInformation.setCreatedBy(systemUser.getUserId());
        trackListDeezerInformation.setCreated(LocalDateTime.now());
        trackListDeezerInformation.setObjectType("ARTIST_TOP");
        trackListDeezerInformation.setStatus(Status.ACTIVE.getValue());
        trackListDeezerInformation.setObjectId(artistId);
        trackListDeezerInformation.setRequest(tracklistUrl);
        trackListDeezerInformation.setName("artist:" + artistFullName);
        ResponseEntity<String> response = restTemplate.exchange(tracklistUrl, HttpMethod.GET, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            trackListDeezerInformation.setResponse(response.getBody());
            deezerIntegrationDAO.persist(trackListDeezerInformation);
        } else {
            LOGGER.error(response.getBody());
        }
    }

    private void fillSongDataUsingTracklist() throws JsonProcessingException {
        var tracklistApiResponse = deezerIntegrationDAO.getForTableFill("Active", "ARTIST_TOP");

        if (tracklistApiResponse.isEmpty()) {
            LOGGER.info("There is no track list responses to go through.");
            return;
        }
        for (var trackList : tracklistApiResponse) {
            var responseData = objectMapper.readValue(trackList.getResponse(), Tracklist.class);
            for (var song : responseData.getData()) {
                var songs = songDAO.getAllSongsWithNameLike(song.getTitleShort());
                if (!songs.isEmpty()) {
                    songDAO.updateDeezerFields(song.getTitleShort(), song.getId(), DeezerStatus.DONE.getValue(),
                            HTMLEncoder.createTableSongInformation(song.getTitle(), song.getTitleShort(), song.getDuration(),
                                    song.getExplicitLyrics(), song.getAlbum().getTitle(),
                                    song.getContributors().stream().map(Contributor::getName).collect(Collectors.toList())));

                    var albums = albumDAO.getAllAlbumsWithNameLike(song.getAlbum().getTitle());
                    if (!albums.isEmpty()) {
                        for (var album : albums) {
                            var media = mediaDAO.findByTypeAndId(COVER_IMAGE_CONST, album.getId());
                            var newMediaStore = new MediaStoreEntity();
                            newMediaStore.setId(UUID.randomUUID().toString());
                            newMediaStore.setName(album.getName());
                            newMediaStore.setType(COVER_IMAGE_CONST);
                            if (media == null) {
                                var newMedia = new MediaEntity();
                                newMedia.setObjectType("ALBUM");
                                newMedia.setObjectId(album.getId());
                                newMedia.setCreated(LocalDateTime.now());
                                newMedia.setCreatedBy(systemUser.getUserId());
                                newMediaStore.setMedia(mediaDAO.persist(newMedia));
                            } else {
                                newMediaStore.setMedia(media);
                            }
                            newMediaStore.setCreated(LocalDateTime.now());
                            newMediaStore.setCreatedBy(systemUser.getUserId());
                            if (!song.getAlbum().getCoverXl().isEmpty()) {
                                newMediaStore.setUrl(song.getAlbum().getCoverXl());
                                newMediaStore.setExtension(
                                        song.getAlbum().getCoverXl().substring(song.getAlbum().getCoverXl().lastIndexOf(".") + 1));
                            } else if (!song.getAlbum().getCover().isEmpty()) {
                                newMediaStore.setUrl(song.getAlbum().getCover());
                                newMediaStore.setExtension(
                                        song.getAlbum().getCover().substring(song.getAlbum().getCover().lastIndexOf(".") + 1));
                            }
                            mediaStoreDAO.persist(newMediaStore);
                        }
                    }
                }
            }
            deezerIntegrationDAO.updateStatus(DeezerStatus.SAVED.getValue(), trackList.getId());
        }
    }

    @Override
    public PayloadResponse<DeezerIntegrationStatisticsResponse> getDataForStatistics(EmptyRequest request) {
        var countSongTotal = songDAO.countAll();
        var countSongDeezer = songDAO.countAllDeezerFields();
        var countArtistTotal = artistDAO.countAll();
        var countArtistDeezer = artistDAO.countAllDeezerFields();
        var songType = new DeezerIntegrationTypes();
        songType.setTableName("SONG");
        songType.setIsFinished(countSongTotal == countSongDeezer);
        songType.setLastModified(songDAO.getLastModified());
        songType.setSequence(1L);
        var artistType = new DeezerIntegrationTypes();
        artistType.setTableName(ARTIST_CONST);
        artistType.setIsFinished(countArtistTotal == countArtistDeezer);
        artistType.setLastModified(artistDAO.getLastModified());
        artistType.setSequence(2L);
        var deezerTypes = new ArrayList<DeezerIntegrationTypes>();
        deezerTypes.add(songType);
        deezerTypes.add(artistType);
        var lastDeezerDoneFields = songDAO.getLastUpdatedDeezerFields();
        var responseStatistics = new DeezerIntegrationStatisticsResponse((long) countSongTotal, (long) countSongDeezer,
                (long) countArtistTotal, (long) countArtistDeezer, deezerTypes, lastDeezerDoneFields);
        return new PayloadResponse<>(request, ResponseCode.OK, responseStatistics);
    }
}