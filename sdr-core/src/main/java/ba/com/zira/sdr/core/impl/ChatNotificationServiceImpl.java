package ba.com.zira.sdr.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.notification.api.model.notify.NotificationTicket;
import ba.com.zira.sdr.api.ChatNotificationService;
import ba.com.zira.sdr.api.model.chat.ChatNotificationRequest;
import ba.com.zira.sdr.api.model.chat.ChatNotificationRequestWrapper;
import ba.com.zira.sdr.core.client.feign.NotificationFeignClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatNotificationServiceImpl implements ChatNotificationService {
    @NonNull
    NotificationFeignClient notificationFeignClient;
    @NonNull
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatNotificationServiceImpl.class);
    @NonNull
    private N2bObjectMapper mapper = new N2bObjectMapper();

    @Override
    public void sendNewPostNotification(EntityRequest<ChatNotificationRequestWrapper> req) throws ApiException {
        LOGGER.info("Sending notification for new post");
        var request = new EntityRequest<>(req.getEntity().getRequest());
        var ticket = prepareTicket("new_post", request, req.getEntity().getReceivers().get(0), "SDR_CHAT_NEW_POST");
        LOGGER.info("Ticket prepared: {}", ticket);
        notificationFeignClient.sendTicket(new EntityRequest<>(ticket, request));
    }

    @Override
    public void sendPostMentionedNotification(EntityRequest<ChatNotificationRequestWrapper> req) throws ApiException {
        LOGGER.info("Sending notification to mentioned users");
        var request = new EntityRequest<>(req.getEntity().getRequest());
        req.getEntity().getReceivers().stream().forEach(receiver -> {
            try {
                var ticket = prepareTicket("post_mention", request, receiver, "SDR_CHAT_POST_MENTION");
                LOGGER.info("Ticket prepared: {}", ticket);
                notificationFeignClient.sendTicket(new EntityRequest<>(ticket, request));
            } catch (Exception ex) {
                LOGGER.error("Error sending notification to {}: {}", receiver, ex.getMessage());
            }
        });

    }

    private NotificationTicket prepareTicket(String templateCode, EntityRequest<ChatNotificationRequest> req, String receiver,
            String logicalUnitCode) throws ApiException {
        NotificationTicket ticket = new NotificationTicket();

        LOGGER.info("Preparing ticket with template {}", templateCode);
        ticket.setMessageTemplate(templateCode);
        try {
            String msg = mapper.writeValueAsString(req.getEntity());
            ticket.setParameter(msg);
            LOGGER.info("Set parameters of ticket as {}", msg);
        } catch (Exception ex) {
            LOGGER.error("Exception while converting string to json: {}", ex.getMessage());
            throw ApiException.createFrom(req, ResponseCode.REQUEST_INVALID);
        }
        ticket.setLogicalUnit(logicalUnitCode);
        ticket.setActivity("Notification");
        ticket.setActivityStep("Send");
        ticket.setExecutionStatus("Send");
        ticket.setDirectInd(false);
        ticket.setUserListTo(receiver);

        LOGGER.info("Set user list to: {}", ticket.getUserListTo());

        return ticket;

    }

}
