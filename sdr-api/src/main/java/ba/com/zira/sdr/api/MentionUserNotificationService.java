
package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.sdr.api.model.comment.MentionUserNotificationRequest;

public interface MentionUserNotificationService {

    public void sendNotificationForMentioningUser(final EntityRequest<MentionUserNotificationRequest> request) throws ApiException;

}
