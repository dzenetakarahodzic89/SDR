package ba.com.zira.sdr.event.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.EventService;
import ba.com.zira.sdr.api.model.event.EventResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "event", description = "Event API")
@RestController
@RequestMapping(value = "event")
@AllArgsConstructor
public class EventRestService {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Find events")
    @GetMapping
    public PagedPayloadResponse<EventResponse> search(final @RequestParam Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        return eventService.find(new FilterRequest(filterCriteria, queryCriteria));
    }
}
