package ba.com.zira.sdr.core.validation;

import org.springframework.stereotype.Component;

import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.ValidationResponse;
import ba.com.zira.commons.model.ValidationError;
import ba.com.zira.commons.model.ValidationErrors;
import ba.com.zira.sdr.api.model.chat.ChatCreateRequest;
import ba.com.zira.sdr.api.model.chat.ChatEntryFindRequest;
import ba.com.zira.sdr.api.model.chat.ChatTopicCreateRequest;
import ba.com.zira.sdr.dao.ChatDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("chatRequestValidation")
public class ChatRequestValidation {

    private ChatDAO chatDAO;

    public ValidationResponse validateChatCreateRequest(final EntityRequest<ChatCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(topicExists(request.getEntity().getChatId()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateChatTopicCreateRequest(final EntityRequest<ChatTopicCreateRequest> request) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(nameExists(request.getEntity().getTopic()));

        return ValidationResponse.of(request, errors);
    }

    public ValidationResponse validateChatEntryFindRequest(final EntityRequest<ChatEntryFindRequest> request) {
        ValidationErrors errors = new ValidationErrors();

        errors.put(topicExists(request.getEntity().getChatId()));

        return ValidationResponse.of(request, errors);
    }

    private ValidationError topicExists(String chatId) {
        if (chatDAO.findByChatId(chatId) == null) {
            return ValidationError.of("TOPIC_NOT_FOUND", "Topic with id: " + chatId + " does not exist!");
        }
        return null;
    }

    private ValidationError nameExists(String name) {
        if (chatDAO.findByTopicName(name) != null) {
            return ValidationError.of("TOPIC_EXISTS", "Topic with name: " + name + " already exists!");
        }
        return null;
    }

}
