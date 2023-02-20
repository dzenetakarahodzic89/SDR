package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMedia;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaCreateRequest;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaUpdateRequest;
import ba.com.zira.sdr.dao.model.ConnectedMediaEntity;

@Mapper(componentModel = "spring")
public interface ConnectedMediaMapper {
    ConnectedMediaEntity dtoToEntity(ConnectedMediaCreateRequest connectedMediaCreateRequest);

    ConnectedMedia entityToDto(ConnectedMediaEntity connectedMediaEntity);

    List<ConnectedMedia> entitiesToDtos(List<ConnectedMediaEntity> connectedMediaEntities);

    void updateEntity(ConnectedMediaUpdateRequest connectedMediaUpdateRequest, @MappingTarget ConnectedMediaEntity connectedMediaEntity);

}
