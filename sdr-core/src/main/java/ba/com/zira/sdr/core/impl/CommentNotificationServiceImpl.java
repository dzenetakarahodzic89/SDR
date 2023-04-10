package ba.com.zira.sdr.core.impl;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.notification.api.model.notify.NotificationTicket;
import ba.com.zira.sdr.api.CommentNotificationService;
import ba.com.zira.sdr.api.model.comment.CommentNotificationRequest;
import ba.com.zira.sdr.core.client.feign.NotificationFeignClient;
import ch.qos.logback.classic.Logger;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class CommentNotificationServiceImpl implements CommentNotificationService {

    NotificationFeignClient notificationFeignClient;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CommentNotificationRequest.class);
    private static final String APP_NOT_MSG = "No template found";
    private static final String UNABLE_JSON = "Unable to parse JSON";

    @Override
    public void sendNotification(EntityRequest<CommentNotificationRequest> req) throws ApiException {
        @NonNull
        NotificationTicket ticket = notify(req);
        notificationFeignClient.sendTicket(new EntityRequest<>(ticket, req));
    }

    public NotificationTicket notify(EntityRequest<CommentNotificationRequest> req) throws ApiException {
        N2bObjectMapper objectMapper = new N2bObjectMapper();
        NotificationTicket ticket = new NotificationTicket();
        String type = "comment_notification";
        ticket.setMessageTemplate(type);
        try {
            String msg = objectMapper.writeValueAsString(req.getEntity());
            ticket.setParameter(msg);
            LOGGER.info(APP_NOT_MSG, msg);
        } catch (Exception e) {
            throw ApiException.createFrom(req, ResponseCode.REQUEST_INVALID, UNABLE_JSON);
        }
        ticket.setLogicalUnit("SDR_COMM_NOTIF");
        ticket.setActivity("NOTIFICATION");
        ticket.setActivityStep("Send");
        ticket.setExecutionStatus("Send");
        ticket.setDirectInd(false);
        ticket.setRequester(req.getEntity().getUserCode());
        ticket.setUserListTo(req.getEntity().getCreatedBy());
        return ticket;
    }
}
