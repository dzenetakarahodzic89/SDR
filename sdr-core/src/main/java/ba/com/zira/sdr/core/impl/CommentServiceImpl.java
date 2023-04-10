package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.CommentNotificationService;
import ba.com.zira.sdr.api.CommentService;
import ba.com.zira.sdr.api.MentionUserNotificationService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentNotificationRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.api.model.comment.CommentsFetchRequest;
import ba.com.zira.sdr.api.model.comment.MentionUserNotificationRequest;
import ba.com.zira.sdr.core.mapper.CommentMapper;
import ba.com.zira.sdr.core.validation.CommentRequestValidation;
import ba.com.zira.sdr.dao.AlbumDAO;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.dao.ChordProgressionDAO;
import ba.com.zira.sdr.dao.CommentDAO;
import ba.com.zira.sdr.dao.EraDAO;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.AlbumEntity;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.ChordProgressionEntity;
import ba.com.zira.sdr.dao.model.CommentEntity;
import ba.com.zira.sdr.dao.model.EraEntity;
import ba.com.zira.sdr.dao.model.InstrumentEntity;
import ba.com.zira.sdr.dao.model.LabelEntity;
import ba.com.zira.sdr.dao.model.PersonEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    CommentNotificationService commentNotificationService;
    CommentDAO commentDAO;
    CommentMapper commentModelMapper;
    CommentRequestValidation commentRequestValidation;
    private SongDAO songDAO;
    private AlbumDAO albumDAO;
    private ArtistDAO artistDAO;
    private LabelDAO labelDAO;
    private InstrumentDAO instrumentDAO;
    private ChordProgressionDAO chordProgressionDAO;
    private EraDAO eraDAO;
    private PersonDAO personDAO;
    MentionUserNotificationService mentionUserNotificationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public PagedPayloadResponse<Comment> find(final FilterRequest request) {
        PagedData<CommentEntity> commentEntities = commentDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, commentEntities, commentModelMapper::entitiesToDtos);
    }

    @Override
    public CommentNotificationRequest createCommentNotificationRequest(EntityRequest<CommentCreateRequest> request) {
        CommentNotificationRequest commentNotification = new CommentNotificationRequest();
        if (request.getEntity().getObjectType() == ObjectType.SONG) {
            SongEntity songEntity = songDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(songEntity.getCreatedBy());
            commentNotification.setObjectName(songEntity.getName());
        }
        if (request.getEntity().getObjectType() == ObjectType.ALBUM) {
            AlbumEntity albumEntity = albumDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(albumEntity.getCreatedBy());
            commentNotification.setObjectName(albumEntity.getName());
        }
        if (request.getEntity().getObjectType() == ObjectType.ARTIST) {
            ArtistEntity artistEntity = artistDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(artistEntity.getCreatedBy());
            commentNotification.setObjectName(artistEntity.getName());
        }
        if (request.getEntity().getObjectType() == ObjectType.PERSON) {
            PersonEntity personEntity = personDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(personEntity.getCreatedBy());
            commentNotification.setObjectName(personEntity.getName());
        }
        if (request.getEntity().getObjectType() == ObjectType.INSTRUMENT) {
            InstrumentEntity instrumentEntity = instrumentDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(instrumentEntity.getCreatedBy());
            commentNotification.setObjectName(instrumentEntity.getName());
        }
        if (request.getEntity().getObjectType() == ObjectType.LABEL) {
            LabelEntity labelEntity = labelDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(labelEntity.getCreatedBy());
            commentNotification.setObjectName(labelEntity.getName());
        }
        if (request.getEntity().getObjectType() == ObjectType.ERA) {
            EraEntity eraEntity = eraDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(eraEntity.getCreatedBy());
            commentNotification.setObjectName(eraEntity.getName());
        }
        if (request.getEntity().getObjectType() == ObjectType.CHORDPROGRESSION) {
            ChordProgressionEntity chordprogressionEntity = chordProgressionDAO.findByPK(request.getEntity().getObjectId());
            commentNotification.setCreatedBy(chordprogressionEntity.getCreatedBy());
            commentNotification.setObjectName(chordprogressionEntity.getName());
        }
        commentNotification.setUserCode(request.getUserId());
        commentNotification.setObjectType(request.getEntity().getObjectType());

        return commentNotification;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Comment> create(final EntityRequest<CommentCreateRequest> request) throws ApiException {
        var commentEntity = commentModelMapper.dtoToEntity(request.getEntity());
        commentEntity.setStatus(Status.ACTIVE.value());
        commentEntity.setCreated(LocalDateTime.now());
        commentEntity.setCreatedBy(request.getUserId());
        commentEntity.setUserCode(request.getUserId());

        commentDAO.persist(commentEntity);
        var commentNotification = createCommentNotificationRequest(request);
        commentNotificationService.sendNotification(new EntityRequest<>(commentNotification, request));

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
