package ba.com.zira.sdr.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.UserRecommendationService;
import ba.com.zira.sdr.api.model.user.UserCodeDisplay;
import ba.com.zira.sdr.api.model.userrecommendation.ScoreCompareRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.api.model.userrecommendation.UserScoreResponse;
import ba.com.zira.sdr.core.client.feign.RemoteApiFeignClient;
import ba.com.zira.sdr.core.mapper.UserRecommendationMapper;
import ba.com.zira.sdr.core.validation.UserRecommendationRequestValidation;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.UserRecommendationDetailDAO;
import ba.com.zira.sdr.dao.UserRecommendationIntegrationDetailDAO;
import ba.com.zira.sdr.dao.model.SongEntity;
import ba.com.zira.sdr.dao.model.UserRecommendationDetailEntity;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;
import ba.com.zira.sdr.dao.model.UserRecommendationIntegrationDetailEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRecommendationServiceImpl implements UserRecommendationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRecommendationServiceImpl.class);

    @NonNull
    UserRecommendationDAO userRecommendationDAO;

    @NonNull
    UserRecommendationDetailDAO userRecommendationDetailDAO;

    @NonNull
    UserRecommendationMapper userRecommendationMapper;

    @NonNull
    UserRecommendationRequestValidation userRecommendationRequestValidation;
    RemoteApiFeignClient remoteApiFeignClient;

    @NonNull
    SongDAO songDAO;

    @NonNull
    UserRecommendationIntegrationDetailDAO userRecommendationIntegrationDetailDAO;

    @Value("${ga.users}")
    List<String> userCodesForGA;

    @Override
    public PagedPayloadResponse<UserRecommendationResponse> find(final FilterRequest request) {
        PagedData<UserRecommendationEntity> userRecommendationEntities = userRecommendationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, userRecommendationEntities, userRecommendationMapper::entitiesToDtos);
    }

    @Override
    public PayloadResponse<UserRecommendationResponse> findById(final EntityRequest<Long> request) {
        userRecommendationRequestValidation.validateExistsUserRecommendationRequest(request);

        var userRecommendationEntity = userRecommendationDAO.findByPK(request.getEntity());

        return new PayloadResponse<>(request, ResponseCode.OK, userRecommendationMapper.entityToDto(userRecommendationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<UserRecommendationResponse> create(final EntityRequest<UserRecommendationCreateRequest> request) {
        var userRecommendationEntity = userRecommendationMapper.dtoToEntity(request.getEntity());
        userRecommendationEntity.setStatus(Status.ACTIVE.value());
        userRecommendationEntity.setCreated(LocalDateTime.now());
        userRecommendationEntity.setCreatedBy(request.getUserId());
        userRecommendationEntity.setUserCode(request.getUserId());
        userRecommendationDAO.persist(userRecommendationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, userRecommendationMapper.entityToDto(userRecommendationEntity));
    }

    @Override
    public ListPayloadResponse<UserScoreResponse> findAllUsers(EmptyRequest request) {
        List<UserScoreResponse> userList = userRecommendationDAO.findAllUsers();
        PagedPayloadResponse<UserCodeDisplay> usersFullName = remoteApiFeignClient.findAllUsers();

        Map<String, String> userCodeToName = new HashMap<>();
        for (UserCodeDisplay user : usersFullName.getPayload()) {
            userCodeToName.put(user.getUsercode(), user.getDisplayname());
        }

        for (UserScoreResponse user : userList) {
            String userName = userCodeToName.get(user.getUserCode());
            if (userName != null) {
                user.setUserCode(userName);
            }
        }

        return new ListPayloadResponse<>(request, ResponseCode.OK, userList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {

        userRecommendationRequestValidation.validateExistsUserRecommendationRequest(request);

        var userRecommendationEntity = userRecommendationDAO.findByPK(request.getEntity());

        userRecommendationDAO.remove(userRecommendationEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, "User Recommendation successfully deleted!");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> generateUserRecommendationsForGA(EmptyRequest request) {
        List<SongEntity> songs = songDAO.findAll();
        userRecommendationDetailDAO.cleanTableForGA();
        userRecommendationDAO.cleanTableForGA();
        userRecommendationIntegrationDetailDAO.cleanTableForGA();

        List<UserRecommendationEntity> entsForGA = createUserRecommendationsForGA();
        userRecommendationDAO.persistCollection(entsForGA);

        userRecommendationDetailDAO.persistCollection(createUserRecommendationDetailsForGA(entsForGA, songs));

        userRecommendationIntegrationDetailDAO.persistCollection(createIntegrationDetails(songs));

        return new PayloadResponse<>(request, ResponseCode.OK, "User Recommendation generated!");
    }

    private List<UserRecommendationIntegrationDetailEntity> createIntegrationDetails(List<SongEntity> songs) {
        List<UserRecommendationIntegrationDetailEntity> list = new ArrayList<>();
        for (SongEntity song : songs) {
            var detail = new UserRecommendationIntegrationDetailEntity();
            detail.setCreated(LocalDateTime.now());
            detail.setSong(song);
            detail.setCreatedBy("GA");
            detail.setStatus(Status.ACTIVE.getValue());
            detail.setName("GA Entry");
            detail.setGenreId(song.getGenre().getId());
            detail.setPlaytimeInSeconds(song.getPlaytimeInSeconds());

            var sdrGrade = BigDecimal.valueOf(Math.random() * 10);
            var spotifyGrade = BigDecimal.valueOf(Math.random() * 10);
            var deezerGrade = BigDecimal.valueOf(Math.random() * 10);
            var tidalGrade = BigDecimal.valueOf(Math.random() * 10);
            var ytGrade = BigDecimal.valueOf(Math.random() * 10);
            var iTunesGrade = BigDecimal.valueOf(Math.random() * 10);
            var gpGrade = BigDecimal.valueOf(Math.random() * 10);

            detail.setSdrScore(sdrGrade.longValue() < 1 ? BigDecimal.valueOf(1) : sdrGrade);
            detail.setSpotifyScore(spotifyGrade.longValue() < 1 ? BigDecimal.valueOf(1) : spotifyGrade);
            detail.setDeezerScore(deezerGrade.longValue() < 1 ? BigDecimal.valueOf(1) : deezerGrade);
            detail.setTidalScore(tidalGrade.longValue() < 1 ? BigDecimal.valueOf(1) : tidalGrade);
            detail.setYoutubeMusicScore(ytGrade.longValue() < 1 ? BigDecimal.valueOf(1) : ytGrade);
            detail.setITunesScore(iTunesGrade.longValue() < 1 ? BigDecimal.valueOf(1) : iTunesGrade);
            detail.setGooglePlayScore(gpGrade.longValue() < 1 ? BigDecimal.valueOf(1) : gpGrade);

            LOGGER.info("Creating recommendation integration for {} ", song.getName());
            list.add(detail);
        }
        return list;
    }

    private List<UserRecommendationDetailEntity> createUserRecommendationDetailsForGA(List<UserRecommendationEntity> entsForGA,
            List<SongEntity> songs) {
        List<UserRecommendationDetailEntity> list = new ArrayList<>();
        LOGGER.info("Found {} songs.", songs.size());
        for (UserRecommendationEntity urEnt : entsForGA) {
            for (SongEntity song : songs) {
                var detail = new UserRecommendationDetailEntity();
                detail.setCreated(LocalDateTime.now());
                detail.setUserRecommendation(urEnt);
                detail.setSong(song);
                detail.setCreatedBy("GA");
                detail.setStatus(Status.ACTIVE.getValue());
                detail.setName("GA Entry");
                var grade = BigDecimal.valueOf(Math.random() * 10);
                detail.setUserScore(grade.longValue() < 1 ? BigDecimal.valueOf(1) : grade);
                LOGGER.info("Creating recommendation by {} for {} : {}", urEnt.getUserCode(), song.getName(), detail.getUserScore());
                list.add(detail);
            }
        }
        return list;
    }

    private List<UserRecommendationEntity> createUserRecommendationsForGA() {
        List<UserRecommendationEntity> list = new ArrayList<>();
        for (String userCode : userCodesForGA) {
            var ent = new UserRecommendationEntity();
            ent.setCreated(LocalDateTime.now());
            ent.setCreatedBy("GA");
            ent.setUserCode(userCode);
            ent.setName("GA Entry");
            ent.setStatus(Status.ACTIVE.getValue());
            ent.setDescription("GA Entry");
            LOGGER.info("Creating recommendation storage for {}", userCode);
            list.add(ent);
        }
        return list;
    }

    @Override
    public ListPayloadResponse<UserScoreResponse> scoreCompare(final EntityRequest<ScoreCompareRequest> request) {

        List<UserScoreResponse> usersList = userRecommendationDAO.averageScoreByGenre(request.getEntity().getUserIds());

        PagedPayloadResponse<UserCodeDisplay> usersFullName = remoteApiFeignClient.findAllUsers();

        Map<String, String> userCodeToName = new HashMap<>();
        for (UserCodeDisplay user : usersFullName.getPayload()) {
            userCodeToName.put(user.getUsercode(), user.getDisplayname());
        }

        for (UserScoreResponse user : usersList) {
            String userName = userCodeToName.get(user.getUserCode());
            if (userName != null) {
                user.setUserCode(userName);
            }
        }

        return new ListPayloadResponse<>(request, ResponseCode.OK, usersList);
    }

}
