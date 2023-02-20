package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.comment.Comment;
import ba.com.zira.sdr.api.model.comment.CommentCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentUpdateRequest;
import ba.com.zira.sdr.dao.model.CommentEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 * 
 * @author zira
 *
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentEntity dtoToEntity(CommentCreateRequest commentCreateRequest);
    Comment entityToDto(CommentEntity commentEntity);
    void updateEntity(CommentUpdateRequest comment, @MappingTarget CommentEntity commentEntity);

    List<Comment> entitiesToDtos(List<CommentEntity> commentEntity);

}