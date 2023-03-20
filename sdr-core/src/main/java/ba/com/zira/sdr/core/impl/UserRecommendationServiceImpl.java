package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import ba.com.zira.sdr.api.model.userrecommendation.AverageScorePerCountry;
import ba.com.zira.sdr.api.model.userrecommendation.ScoreCompareRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.core.mapper.UserRecommendationMapper;
import ba.com.zira.sdr.core.validation.UserRecommendationRequestValidation;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRecommendationServiceImpl implements UserRecommendationService {

    UserRecommendationDAO userRecommendationDAO;
    UserRecommendationMapper userRecommendationMapper;
    UserRecommendationRequestValidation userRecommendationRequestValidation;

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
    public ListPayloadResponse<UserRecommendationResponse> findAllUsers(EmptyRequest req) {

        List<UserRecommendationResponse> userList = userRecommendationDAO.findAllUsers();
        return new ListPayloadResponse<>(req, ResponseCode.OK, userList);
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
    public ListPayloadResponse<UserRecommendationResponse> scoreCompare(final EntityRequest<ScoreCompareRequest> request) {

        List<UserRecommendationResponse> userSearch = userRecommendationDAO.averageScoreByGenre(request.getEntity().getUserIds());

        return new ListPayloadResponse<>(request, ResponseCode.OK, userSearch);
    }

    @Override
    public ListPayloadResponse<AverageScorePerCountry> getAverageScorePerCountry(EntityRequest<Pair<String, String>> request) {
        userRecommendationRequestValidation.validateAverageScorePerCountryRequest(request);
        var results = userRecommendationDAO.getAverageScorePerCountryForUser(request.getEntity().getKey(), request.getEntity().getValue());

        return new ListPayloadResponse<>(request, ResponseCode.OK, results);
    }

}
