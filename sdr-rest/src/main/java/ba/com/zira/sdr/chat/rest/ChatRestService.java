package ba.com.zira.sdr.chat.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.sdr.api.ChatService;
import ba.com.zira.sdr.api.model.chat.Chat;
import ba.com.zira.sdr.api.model.chat.ChatCreateRequest;
import ba.com.zira.sdr.api.model.chat.ChatEntryFindRequest;
import ba.com.zira.sdr.api.model.chat.ChatTopic;
import ba.com.zira.sdr.api.model.chat.ChatTopicCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "chat", description = "Chat API")
@RestController
@RequestMapping(value = "chat")
@AllArgsConstructor
public class ChatRestService {
    private ChatService chatService;

    @Operation(summary = "Find chat entries based on filter criteria")
    @GetMapping
    public PagedPayloadResponse<Chat> find(@RequestParam(required = true) String chatId,
            @RequestParam final Map<String, Object> filterCriteria, final QueryConditionPage queryCriteria) throws ApiException {
        var filterRequest = new FilterRequest(filterCriteria, queryCriteria);
        return chatService.findChatEntries(new EntityRequest<ChatEntryFindRequest>(new ChatEntryFindRequest(chatId, filterRequest)));
    }

    @Operation(summary = "Find chat topics")
    @GetMapping(value = "/topic")
    public PagedPayloadResponse<ChatTopic> findChatTopics(@RequestParam final Map<String, Object> filterCriteria,
            final QueryConditionPage queryCriteria) throws ApiException {
        var request = new FilterRequest(filterCriteria, queryCriteria);
        return chatService.findChatTopics(request);
    }

    @Operation(summary = "Create a chat entry")
    @PostMapping
    public PayloadResponse<Chat> createChatEntry(@RequestBody final ChatCreateRequest request) throws ApiException {
        return chatService.createChatEntry(new EntityRequest<>(request));
    }

    @Operation(summary = "Create a chat topic")
    @PostMapping(value = "/topic")
    public PayloadResponse<ChatTopic> createChatTopic(@RequestBody final ChatTopicCreateRequest request) throws ApiException {
        return chatService.createTopic(new EntityRequest<>(request));
    }

}
