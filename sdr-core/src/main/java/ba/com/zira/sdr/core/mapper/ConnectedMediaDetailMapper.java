package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailInfo;
import ba.com.zira.sdr.dao.model.ConnectedMediaDetailEntity;

@Mapper(componentModel = "spring")
public interface ConnectedMediaDetailMapper {

    ConnectedMediaDetailInfo entityToInfoDto(ConnectedMediaDetailEntity connectedMediaEntity);

    List<ConnectedMediaDetailInfo> entitiesToInfoDtos(List<ConnectedMediaDetailEntity> connectedMediaEntities);

}
