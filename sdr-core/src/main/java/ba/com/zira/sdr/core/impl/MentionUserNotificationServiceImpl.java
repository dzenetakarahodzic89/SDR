
package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.notification.api.model.notify.NotificationTicket;
import ba.com.zira.sdr.api.MentionUserNotificationService;
import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.comment.MentionUserNotificationRequest;
import ba.com.zira.sdr.core.client.feign.NotificationFeignClient;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MentionUserNotificationServiceImpl implements MentionUserNotificationService {

    NotificationFeignClient notificationFeignClient;

    @Override
    public void sendNotificationForMentioningUser(final EntityRequest<MentionUserNotificationRequest> request) throws ApiException {
        for (String mentionTarget : request.getEntity().getMentionTargets()) {
            NotificationTicket ticket = prepareTicketForMentioningUser("comment_mention", request, mentionTarget);
            notificationFeignClient.sendTicket(new EntityRequest<>(ticket, request));
        }
    }

    private NotificationTicket prepareTicketForMentioningUser(final String templateCode,
            final EntityRequest<MentionUserNotificationRequest> request, final String mentionTarget) throws ApiException {
        NotificationTicket ticket = new NotificationTicket();

        ticket.setMessageTemplate(templateCode);
        ticket.setFrom(request.getUserId());
        ticket.setRequester(request.getUserId());
        ticket.setRecipient(mentionTarget);

        try {
            ticket.setParameter(prepareParameterValues(request.getEntity().getCommentContent(), request.getUserId(),
                    request.getEntity().getObjectType(), request.getEntity().getObjectName(), request.getEntity().getOverviewUrl()));
        } catch (Exception ex) {
            throw ApiException.createFrom(request, ResponseCode.REQUEST_INVALID, "Unable to convert JSON!");
        }

        ticket.setLogicalUnit("SDR_COMMENT_MENTION");
        ticket.setActivity("Notification");
        ticket.setActivityStep("Send");
        ticket.setExecutionStatus("Send");
        ticket.setDirectInd(true);
        ticket.setUserListTo(mentionTarget);

        return ticket;

    }

    private String prepareParameterValues(final String commentContent, final String mentionInitiator, final ObjectType objectType,
            final String objectName, final String overviewUrl) {
        N2bObjectMapper objectMapper = new N2bObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put("commentContent", commentContent);
        node.put("mentionInitiator", mentionInitiator);
        node.put("objectType", objectType.toString());
        node.put("objectName", objectName);
        node.put("overviewUrl", overviewUrl);

        return node.toString();
    }

}
