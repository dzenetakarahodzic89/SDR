package ba.com.zira.sdr.core.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.CommentService;
import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
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

    @Override
    public PagedPayloadResponse<Comment> find(final FilterRequest request) throws ApiException {
        PagedData<CommentEntity> commentEntities = commentDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, commentEntities, commentModelMapper::entitiesToDtos);
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
        return new PayloadResponse<>(request,ResponseCode.OK, commentModelMapper.entityToDto(commentEntity));
    }

    
      @Override
      
      @Transactional(rollbackFor = Exception.class)
      public PayloadResponse<Comment> update(final EntityRequest<CommentUpdateRequest> request) throws ApiException {
      commentRequestValidation.validateUpdateCommentModelRequest(request);
      
      var commentEntity = commentDAO.findByPK(request.getEntity().getId());
      commentModelMapper.updateEntity(request.getEntity(), commentEntity);
      
      commentEntity.setModified(LocalDateTime.now());
      commentEntity.setModifiedBy(request.getUserId());
      commentDAO.merge(commentEntity);
      return new PayloadResponse<>(request,ResponseCode.OK, commentModelMapper.entityToDto(commentEntity));
      
      }
     
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<Comment> delete(final EntityRequest<Long> request) {
        commentRequestValidation.validateExistsCommentModelRequest(request);

        CommentEntity commentEntity = commentDAO.findByPK(request.getEntity());

        commentDAO.removeByPK(request.getEntity());
        return new PayloadResponse<>(request, ResponseCode.OK, commentModelMapper.entityToDto(commentEntity));
    }
}