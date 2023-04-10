package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.ChatNotificationService;
import ba.com.zira.sdr.api.ChatService;
import ba.com.zira.sdr.api.model.chat.Chat;
import ba.com.zira.sdr.api.model.chat.ChatCreateRequest;
import ba.com.zira.sdr.api.model.chat.ChatEntryFindRequest;
import ba.com.zira.sdr.api.model.chat.ChatNotificationRequest;
import ba.com.zira.sdr.api.model.chat.ChatNotificationRequestWrapper;
import ba.com.zira.sdr.api.model.chat.ChatTopic;
import ba.com.zira.sdr.api.model.chat.ChatTopicCreateRequest;
import ba.com.zira.sdr.core.mapper.ChatMapper;
import ba.com.zira.sdr.core.validation.ChatRequestValidation;
import ba.com.zira.sdr.dao.ChatDAO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    @NonNull
    private ChatDAO chatDAO;
    @NonNull
    private ChatMapper chatMapper;
    @NonNull
    private ChatRequestValidation chatRequestValidation;
    @NonNull
    private ChatNotificationService notificationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Override
    public PagedPayloadResponse<Chat> findChatEntries(final EntityRequest<ChatEntryFindRequest> request) throws ApiException {
        chatRequestValidation.validateChatEntryFindRequest(request);

        PagedData<Chat> chatEntries = chatDAO.findChatEntries(request.getEntity().getChatId(),
                request.getEntity().getFilterRequest().getFilter().getPaginationFilter().getPage(),
                request.getEntity().getFilterRequest().getFilter().getPaginationFilter().getEntitiesPerPage());

        return new PagedPayloadResponse<>(request, ResponseCode.OK, chatEntries);
    }

    @Override
    public PayloadResponse<ChatTopic> createTopic(EntityRequest<ChatTopicCreateRequest> request) throws ApiException {
        chatRequestValidation.validateChatTopicCreateRequest(request);

        var chatEntity = chatMapper.topicDtoToEntity(request.getEntity());

        chatEntity.setStatus(Status.ACTIVE.value());
        chatEntity.setCreated(LocalDateTime.now());
        chatEntity.setCreatedBy(request.getUserId());
        chatEntity.setUserCode(request.getUserId());
        chatEntity.setChatId(UUID.randomUUID().toString());

        chatDAO.persist(chatEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, chatMapper.entityToTopicDto(chatEntity));
    }

    @Override
    public PayloadResponse<Chat> createChatEntry(EntityRequest<ChatCreateRequest> request) throws ApiException {
        chatRequestValidation.validateChatCreateRequest(request);

        var chatEntity = chatMapper.chatDtoToEntity(request.getEntity());

        chatEntity.setStatus(Status.ACTIVE.value());
        chatEntity.setCreated(LocalDateTime.now());
        chatEntity.setCreatedBy(request.getUserId());
        chatEntity.setUserCode(request.getUserId());

        chatDAO.persist(chatEntity);

        ChatNotificationRequestWrapper chatNotificationRequestWrapper = new ChatNotificationRequestWrapper();
        ChatNotificationRequest chatNotificationRequest = new ChatNotificationRequest();
        chatNotificationRequest.setContent(request.getEntity().getContent());
        chatNotificationRequest.setPoster(request.getUserId());
        LOGGER.info("Creator of the post is {}.", request.getUserId());
        chatNotificationRequest.setTopic(request.getEntity().getTopic());
        var topicCreator = chatDAO.getUserCodeOfTopicCreator(request.getEntity().getChatId());
        LOGGER.info("Topic creator is {}.", topicCreator);
        chatNotificationRequestWrapper.setReceivers(Arrays.asList(topicCreator));
        chatNotificationRequestWrapper.setRequest(chatNotificationRequest);

        /**
         * Send a notification to the creator of the topic only if the user that
         * created the post isn't the topic creator.
         **/
        if (!topicCreator.equals(request.getUserId())) {
            notificationService.sendNewPostNotification(new EntityRequest<>(chatNotificationRequestWrapper));
        }

        var mentionedUsers = parseMentions(request.getEntity().getContent()).stream().filter(m -> !m.equals(request.getUserId()))
                .collect(Collectors.toSet());

        /**
         * If there are mentioned users in the content of the post notify all of
         * them except for the post creator.
         **/
        if (!mentionedUsers.isEmpty()) {
            LOGGER.info("Mentioned users are {}", mentionedUsers);
            chatNotificationRequestWrapper.setReceivers(new ArrayList<>(mentionedUsers));
            notificationService.sendPostMentionedNotification(new EntityRequest<>(chatNotificationRequestWrapper));
        }

        return new PayloadResponse<>(request, ResponseCode.OK, chatMapper.entityToChatDto(chatEntity));
    }

    @Override
    public PagedPayloadResponse<ChatTopic> findChatTopics(FilterRequest request) throws ApiException {
        PagedData<ChatTopic> chatTopics = chatDAO.findTopics(request.getFilter().getPaginationFilter().getPage(),
                request.getFilter().getPaginationFilter().getEntitiesPerPage());

        return new PagedPayloadResponse<>(request, ResponseCode.OK, chatTopics);
    }

    private List<String> parseMentions(String content) {
        var mentionedUsers = new ArrayList<String>();
        Matcher m = Pattern.compile("(?<=@)[\\w\\.]+\\b").matcher(content);

        while (m.find()) {
            mentionedUsers.add(m.group());
        }

        return mentionedUsers;
    }

}
