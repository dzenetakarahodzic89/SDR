package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.CommentService;
import ba.com.zira.sdr.api.MentionUserNotificationService;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.api.model.comment.CommentsFetchRequest;
import ba.com.zira.sdr.api.model.comment.MentionUserNotificationRequest;
import ba.com.zira.sdr.core.mapper.CommentMapper;
import ba.com.zira.sdr.core.validation.CommentRequestValidation;
import ba.com.zira.sdr.dao.CommentDAO;
import ba.com.zira.sdr.dao.model.CommentEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    CommentDAO commentDAO;
    CommentMapper commentModelMapper;
    CommentRequestValidation commentRequestValidation;
    MentionUserNotificationService mentionUserNotificationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public PagedPayloadResponse<Comment> find(final FilterRequest request) {
        PagedData<CommentEntity> commentEntities = commentDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, commentEntities, commentModelMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Comment> create(final EntityRequest<CommentCreateRequest> request) {
        var commentEntity = commentModelMapper.dtoToEntity(request.getEntity());
        commentEntity.setStatus(Status.ACTIVE.value());
        commentEntity.setCreated(LocalDateTime.now());
        commentEntity.setCreatedBy(request.getUserId());
        commentEntity.setUserCode(request.getUserId());
        commentDAO.persist(commentEntity);

        if (!request.getEntity().getMentionTargets().isEmpty()) {

            MentionUserNotificationRequest mentionUserNotificationReq = new MentionUserNotificationRequest();
            mentionUserNotificationReq.setCommentContent(request.getEntity().getContent());
            mentionUserNotificationReq.setMentionInitiator(request.getEntity().getCreatedBy());
            mentionUserNotificationReq.setMentionTargets(request.getEntity().getMentionTargets());
            mentionUserNotificationReq.setObjectType(request.getEntity().getObjectType());
            mentionUserNotificationReq.setObjectName(request.getEntity().getObjectName());
            mentionUserNotificationReq.setOverviewUrl(request.getEntity().getOverviewUrl());

            try {
                mentionUserNotificationService.sendNotificationForMentioningUser(new EntityRequest<>(mentionUserNotificationReq, request));
            } catch (Exception e) {
                LOGGER.error("sendNotificationForMentioningUser => {}", e.getMessage());
            }
        }

        return new PayloadResponse<>(request, ResponseCode.OK, commentModelMapper.entityToDto(commentEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Comment> update(final EntityRequest<CommentUpdateRequest> request) {
        commentRequestValidation.validateUpdateCommentModelRequest(request);

        var commentEntity = commentDAO.findByPK(request.getEntity().getId());
        commentModelMapper.updateEntity(request.getEntity(), commentEntity);

        commentEntity.setModified(LocalDateTime.now());
        commentEntity.setModifiedBy(request.getUserId());
        commentDAO.merge(commentEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, commentModelMapper.entityToDto(commentEntity));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        commentRequestValidation.validateExistsCommentModelRequest(request);
        commentDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK,
                String.format("Comment with id %s is successfully deleted!", request.getEntity()));
    }

    @Override
    public ListPayloadResponse<Comment> fetchComments(final EntityRequest<CommentsFetchRequest> request) {

        List<Comment> comments = commentDAO.fetchComments(request.getEntity().getObjectType(), request.getEntity().getObjectId());

        return new ListPayloadResponse<>(request, ResponseCode.OK, comments);
    }

}
