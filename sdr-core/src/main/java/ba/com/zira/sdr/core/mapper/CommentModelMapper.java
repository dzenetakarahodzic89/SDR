package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.comment.CommentModel;
import ba.com.zira.sdr.api.model.comment.CommentModelCreateRequest;
import ba.com.zira.sdr.api.model.comment.CommentModelUpdateRequest;
import ba.com.zira.sdr.dao.model.CommentEntity;

/**
 * Defined mapper interface for mapping a DTO to Entity model class
 * 
 * @author zira
 *
 */
@Mapper(componentModel = "spring")
public interface CommentModelMapper {


    CommentEntity dtoToEntity(CommentModelCreateRequest commentModelCreateRequest);
    
 
    CommentModel entityToDto(CommentEntity commentEntity);

  
    void updateEntity(CommentModelUpdateRequest commentModel, @MappingTarget CommentEntity commentModelEntity);

    List<CommentModel> entitiesToDtos(List<CommentEntity> commentModelEntity);

}