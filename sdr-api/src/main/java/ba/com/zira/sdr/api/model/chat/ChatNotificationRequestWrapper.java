package ba.com.zira.sdr.api.model.chat;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatNotificationRequestWrapper implements Serializable {

    private List<String> receivers;
    ChatNotificationRequest request;

}
