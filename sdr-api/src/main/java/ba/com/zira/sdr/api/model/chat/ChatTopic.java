package ba.com.zira.sdr.api.model.chat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the chat topic")
    private String chatId;
    @Schema(description = "Title of the chat topic")
    private String topic;
    @Schema(description = "User code of the user that created the chat topic")
    private String userCode;
    @Schema(description = "Creation date")
    private LocalDate created;

    public ChatTopic(String chatId, String topic, String userCode, LocalDateTime created) {
        this.userCode = userCode;
        this.created = created != null
                ? LocalDate.parse(created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : null;
        this.chatId = chatId;
        this.topic = topic;
    }

}
