package ba.com.zira.sdr.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.notification.api.model.notify.NotificationTicket;
import ba.com.zira.sdr.api.model.battle.BattleFinishedNotificationRequest;
import ba.com.zira.sdr.core.client.feign.NotificationFeignClient;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BattleNotificationService {

    NotificationFeignClient notificationFeignClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleNotificationService.class);
    private static final String TEMPLATE = "Template";
    private static final String UNABLE_JSON = "Unable to parse json";

    public void sendNotification(EntityRequest<BattleFinishedNotificationRequest> req) throws ApiException {
        NotificationTicket ticket = prepareBattleFinishedNotification(req);
        notificationFeignClient.sendTicket(new EntityRequest<>(ticket, req));
    }

    private NotificationTicket prepareBattleFinishedNotification(EntityRequest<BattleFinishedNotificationRequest> req) throws ApiException {
        N2bObjectMapper objectMapper = new N2bObjectMapper();
        NotificationTicket ticket = new NotificationTicket();
        String type = "battle_finish";
        ticket.setMessageTemplate(type);
        try {
            String msg = objectMapper.writeValueAsString(req.getEntity());
            ticket.setParameter(msg);
            LOGGER.info(TEMPLATE, msg);
        } catch (Exception ex) {
            throw ApiException.createFrom(req, ResponseCode.REQUEST_INVALID, UNABLE_JSON);
        }
        ticket.setLogicalUnit("SDR_BATTLE_TURN_FINISH");
        ticket.setActivity("Notification");
        ticket.setActivityStep("Send");
        ticket.setExecutionStatus("Send");
        ticket.setDirectInd(false);
        ticket.setUserListTo(req.getEntity().getUserSentTo());
        return ticket;
    }
}
