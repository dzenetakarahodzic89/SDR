package ba.com.zira.sdr.core.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.notification.api.model.notify.Notification;
import ba.com.zira.notification.api.model.notify.NotificationTicket;

@Service
@FeignClient(name = "${notification.service.location}", decode404 = true)
public interface NotificationFeignClient {

    @PostMapping(value = "/notification/prepare")
    PagedPayloadResponse<Notification> prepare(@RequestBody final EntityRequest<NotificationTicket> request) throws ApiException;

    @PostMapping(value = "/notification/direct/send")
    void send(@RequestBody final EntityRequest<Notification> request);

    @PostMapping(value = "/notification/send")
    PagedPayloadResponse<Long> sendTicket(@RequestBody final EntityRequest<NotificationTicket> request) throws ApiException;
}