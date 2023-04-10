package ba.com.zira.sdr.api.model.chat;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Properties needed to create a chat entry")
public class ChatCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Unique identifier of the chat topic")
    String chatId;

    @NotBlank
    @Schema(description = "Title of the chat topic")
    String topic;

    @NotBlank
    @Schema(description = "Content of the chat entry")
    String content;

}
