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
@Schema(description = "Properties needed to create a chat topic")
public class ChatTopicCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Title of the chat topic")
    String topic;

    @NotBlank
    @Schema(description = "Content of the first chat entry")
    String content;

}
