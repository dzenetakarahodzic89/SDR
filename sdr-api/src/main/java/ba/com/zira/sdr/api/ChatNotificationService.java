package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.sdr.api.model.chat.ChatNotificationRequestWrapper;

public interface ChatNotificationService {

    public void sendNewPostNotification(EntityRequest<ChatNotificationRequestWrapper> req) throws ApiException;

    public void sendPostMentionedNotification(EntityRequest<ChatNotificationRequestWrapper> req) throws ApiException;
}
