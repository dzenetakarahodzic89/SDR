package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetail;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailCreateRequest;
import ba.com.zira.sdr.api.model.connectedmediadetail.ConnectedMediaDetailInfo;
import ba.com.zira.sdr.dao.model.ConnectedMediaDetailEntity;

@Mapper(componentModel = "spring")
public interface ConnectedMediaDetailMapper {
    ConnectedMediaDetailEntity dtoToEntity(ConnectedMediaDetailCreateRequest connectedMediaDetailCreateRequest);

    ConnectedMediaDetailInfo entityToInfoDto(ConnectedMediaDetailEntity connectedMediaDetailEntity);

    List<ConnectedMediaDetailInfo> entitiesToInfoDtos(List<ConnectedMediaDetailEntity> connectedMediaDetailEntities);

    ConnectedMediaDetail entityToDto(ConnectedMediaDetailEntity connectedMediaDetailEntity);

    List<ConnectedMediaDetail> entitiesToDtos(List<ConnectedMediaDetailEntity> connectedMediaDetailEntities);

}
