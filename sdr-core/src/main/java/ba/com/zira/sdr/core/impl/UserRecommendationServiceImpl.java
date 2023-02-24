package ba.com.zira.sdr.core.impl;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.UserRecommendationService;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendation.UserRecommendationResponse;
import ba.com.zira.sdr.core.mapper.UserRecommendationMapper;
import ba.com.zira.sdr.core.validation.UserRecommendationRequestValidation;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.model.UserRecommendationEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {

        userRecommendationRequestValidation.validateExistsUserRecommendationRequest(request);

        var userRecommendationEntity = userRecommendationDAO.findByPK(request.getEntity());

        userRecommendationDAO.remove(userRecommendationEntity);

        return new PayloadResponse<>(request, ResponseCode.OK, "User Recommendation successfully deleted!");

    }

}
