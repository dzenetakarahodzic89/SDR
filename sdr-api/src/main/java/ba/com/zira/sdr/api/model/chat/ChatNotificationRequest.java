package ba.com.zira.sdr.api.model.chat;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatNotificationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String poster;
    private String content;
    private String topic;

}
