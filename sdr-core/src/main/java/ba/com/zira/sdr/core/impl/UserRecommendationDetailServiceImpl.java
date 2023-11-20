package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.UserRecommendationDetailService;
import ba.com.zira.sdr.api.model.userrecommendationdetail.ResponseUserRecommendedDetailSongs;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailCreateRequest;
import ba.com.zira.sdr.api.model.userrecommendationdetail.UserRecommendationDetailResponse;
import ba.com.zira.sdr.core.mapper.UserRecommendationDetailMapper;
import ba.com.zira.sdr.core.validation.UserRecommendationDetailRequestValidation;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.UserRecommendationDAO;
import ba.com.zira.sdr.dao.UserRecommendationDetailDAO;
import ba.com.zira.sdr.dao.model.UserRecommendationDetailEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRecommendationDetailServiceImpl implements UserRecommendationDetailService {

    UserRecommendationDetailDAO userRecommendationDetailDAO;
    UserRecommendationDAO userRecommendationDAO;
    SongDAO songDAO;

    UserRecommendationDetailMapper userRecommendationDetailMapper;
    UserRecommendationDetailRequestValidation userRecommendationDetailRequestValidation;

    @Override
    public PagedPayloadResponse<UserRecommendationDetailResponse> find(final FilterRequest filterRequest)
            throws ApiException {
        PagedData<UserRecommendationDetailEntity> userRecommendationDetailEntities = userRecommendationDetailDAO
                .findAll(filterRequest.getFilter());
        return new PagedPayloadResponse<>(filterRequest, ResponseCode.OK, userRecommendationDetailEntities,
                userRecommendationDetailMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<UserRecommendationDetailResponse> create(
            final EntityRequest<UserRecommendationDetailCreateRequest> entityRequest) throws ApiException {
        userRecommendationDetailRequestValidation.validateCreateUserRecommendationDetailRequest(entityRequest);

        UserRecommendationDetailEntity userRecommendationDetailEntity = userRecommendationDetailMapper
                .dtoToEntity(entityRequest.getEntity());

        userRecommendationDetailEntity.setStatus(Status.INACTIVE.value());
        userRecommendationDetailEntity.setCreated(LocalDateTime.now());
        userRecommendationDetailEntity.setCreatedBy(entityRequest.getUserId());

        userRecommendationDetailEntity.setUserRecommendation(
                userRecommendationDAO.findByPK(userRecommendationDetailEntity.getUserRecommendation().getId()));
        userRecommendationDetailEntity.setSong(songDAO.findByPK(userRecommendationDetailEntity.getSong().getId()));

        userRecommendationDetailDAO.persist(userRecommendationDetailEntity);

        return new PayloadResponse<>(entityRequest, ResponseCode.OK,
                userRecommendationDetailMapper.entityToDto(userRecommendationDetailEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> entityRequest) {
        userRecommendationDetailRequestValidation.validateDeleteUserRecommendationDetailRequest(entityRequest);
        userRecommendationDetailDAO.removeByPK(entityRequest.getEntity());

        return new PayloadResponse<>(entityRequest, ResponseCode.OK, "Deleted record successfully!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ListPayloadResponse<ResponseUserRecommendedDetailSongs> getTopTenRecommendedSongs(EmptyRequest req) throws ApiException {
        List<ResponseUserRecommendedDetailSongs> responseList = userRecommendationDetailDAO.findTopTenRatedSongs();

        return new ListPayloadResponse<>(req,ResponseCode.OK,responseList);

    }



}
