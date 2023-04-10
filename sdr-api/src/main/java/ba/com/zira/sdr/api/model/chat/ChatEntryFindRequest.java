package ba.com.zira.sdr.api.model.chat;

import java.io.Serializable;

import ba.com.zira.commons.message.request.FilterRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntryFindRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of chat topic")
    String chatId;

    @Schema(description = "Filters and pagination criteria")
    FilterRequest filterRequest;

}
