package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.mapper.DeezerIntegrationMapper;
import ba.com.zira.sdr.core.validation.DeezerIntegrationRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.DeezerIntegrationDAO;
import ba.com.zira.sdr.dao.MediaDAO;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;
import ba.com.zira.sdr.dao.model.MediaEntity;
import ba.com.zira.sdr.dao.model.MediaStoreEntity;
import ba.com.zira.sdr.deezer.ArtistSearch;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeezerIntegrationServiceImpl implements DeezerIntegrationService {

    DeezerIntegrationDAO deezerIntegrationDAO;
    DeezerIntegrationMapper deezerIntegrationMapper;
    DeezerIntegrationRequestValidation deezerIntegrationRequestValidation;
    ArtistDAO artistDAO;
    ObjectMapper objectMapper;
    MediaDAO mediaDAO;
    MediaStoreDAO mediaStoreDAO;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeezerIntegrationServiceImpl.class);
    private static final User systemUser = new User("System");

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

    @Scheduled(initialDelay = 100, fixedDelay = 300000L)
    public void getArtistInformationFromDeezer() {
        List<LoV> artistsForSearch = artistDAO.getArtistsForDeezerSearch();
        for (var artist : artistsForSearch) {
            var artistDeezerInformation = new DeezerIntegrationEntity();
            artistDeezerInformation.setId(UUID.randomUUID().toString());
            artistDeezerInformation.setCreatedBy(systemUser.getUserId());
            artistDeezerInformation.setCreated(LocalDateTime.now());
            artistDeezerInformation.setObjectType("ARTIST");
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
        var artistApiResponses = deezerIntegrationDAO.getForTableFill();
        if (artistApiResponses.isEmpty()) {
            LOGGER.info("There is no artist responses to go through.");
            return;
        }
        for (var artistApiResponse : artistApiResponses) {
            var responseData = objectMapper.readValue(artistApiResponse.getResponse(), ArtistSearch.class);
            for (var entity : responseData.getData()) {

                if (entity.getName().equalsIgnoreCase(artistApiResponse.getName().substring(7))) {
                    artistDAO.updateDeezerFields(artistApiResponse.getObjectId(), (long) entity.getId(), (long) entity.getNbFan());
                }
                if (entity.getName().contains(artistApiResponse.getName().substring(7))) {
                    var newMediaStore = new MediaStoreEntity();
                    newMediaStore.setId(UUID.randomUUID().toString());
                    newMediaStore.setName(artistApiResponse.getName().substring(7));
                    newMediaStore.setType("COVER_IMAGE");
                    var media = mediaDAO.findByTypeAndId("ARTIST", artistApiResponse.getObjectId());
                    if (media == null) {
                        var newMedia = new MediaEntity();
                        newMedia.setObjectType("ARTIST");
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
}