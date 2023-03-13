package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.EventService;
import ba.com.zira.sdr.api.model.event.EventResponse;
import ba.com.zira.sdr.core.mapper.EventMapper;
import ba.com.zira.sdr.dao.EventDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventDAO eventDAO;
    EventMapper eventMapper;

    @Override
    public PagedPayloadResponse<EventResponse> find(FilterRequest request) throws ApiException {
        var eventEntities = eventDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, eventEntities, eventMapper::entitiesToDtos);
    }

}
