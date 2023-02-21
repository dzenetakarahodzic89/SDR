package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailInfo;
import ba.com.zira.sdr.dao.model.ConnectedMediaDetailEntity;

@Mapper(componentModel = "spring")
public interface ConnectedMediaDetailMapper {

    ConnectedMediaDetailInfo entityToDto(ConnectedMediaDetailEntity connectedMediaEntity);

    List<ConnectedMediaDetailInfo> entitiesToDtos(List<ConnectedMediaDetailEntity> connectedMediaEntities);

}
