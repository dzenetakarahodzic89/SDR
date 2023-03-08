package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegration;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequest;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationCreateRequestExtend;
import ba.com.zira.sdr.api.model.deezerintegration.DeezerIntegrationUpdateRequest;
import ba.com.zira.sdr.api.model.lov.LoV;
import ba.com.zira.sdr.core.mapper.DeezerIntegrationMapper;
import ba.com.zira.sdr.core.validation.DeezerIntegrationRequestValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.DeezerIntegrationDAO;
import ba.com.zira.sdr.dao.model.DeezerIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeezerIntegrationServiceImpl implements DeezerIntegrationService {

    DeezerIntegrationDAO deezerIntegrationDAO;
    DeezerIntegrationMapper deezerIntegrationMapper;
    DeezerIntegrationRequestValidation deezerIntegrationRequestValidation;
    ArtistDAO artistDAO;
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
    public PayloadResponse<String> delete(final EntityRequest<Long> request) throws ApiException {
        deezerIntegrationRequestValidation.validateExistsDeezerIntegrationRequest(request);

        var deezerIntegrationEntity = deezerIntegrationDAO.findByPK(request.getEntity());
        deezerIntegrationDAO.remove(deezerIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "Deezer integration is removed.");
    }

    @Scheduled(initialDelay = 100, fixedDelay = 10000L)
    public void getArtistInformationFromDeezer() {
        List<LoV> artistsForSearch = artistDAO.getArtistsForDeezerSearch();
        for (var artist : artistsForSearch) {
            var artistDeezerInformation = new DeezerIntegrationCreateRequestExtend();
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
                deezerIntegrationDAO.persist(deezerIntegrationMapper.dtoToEntityExtended(artistDeezerInformation));
            } else {
                LOGGER.error("Error", response.getBody());
            }
        }

        System.out.println(artistsForSearch);
    }
}