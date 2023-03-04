package ba.com.zira.sdr.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ba.com.zira.sdr.api.model.fileuploadsegment.FileUploadSegmentCreateRequest;
import ba.com.zira.sdr.dao.model.FileUploadSegmentEntity;

@Mapper(componentModel = "spring")
public interface FileUploadSegmentMapper {
    @Mapping(source = "id", target = "media.id")
    FileUploadSegmentEntity dtoToEntity(FileUploadSegmentCreateRequest request);
}
