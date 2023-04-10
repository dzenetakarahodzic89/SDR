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
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the chat entry")
    private Long id;
    @Schema(description = "User code of the usre that created the chat entry")
    private String userCode;
    @Schema(description = "Creation date")
    private LocalDate created;
    @Schema(description = "Content of the chat entry")
    private String content;
    @Schema(description = "Unique identifier of the chat topic")
    private String chatId;
    @Schema(description = "Title of the chat topic")
    private String topic;

    public Chat(Long id, String userCode, LocalDateTime created, String content, String chatId, String topic) {
        this.id = id;
        this.userCode = userCode;
        this.created = created != null
                ? LocalDate.parse(created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : null;
        this.content = content;
        this.chatId = chatId;
        this.topic = topic;
    }

}
