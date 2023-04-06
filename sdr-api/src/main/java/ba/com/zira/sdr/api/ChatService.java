package ba.com.zira.sdr.api;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.chat.Chat;
import ba.com.zira.sdr.api.model.chat.ChatCreateRequest;
import ba.com.zira.sdr.api.model.chat.ChatEntryFindRequest;
import ba.com.zira.sdr.api.model.chat.ChatTopic;
import ba.com.zira.sdr.api.model.chat.ChatTopicCreateRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface ChatService.
 */
public interface ChatService {

    /**
     * Find chat entries.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<Chat> findChatEntries(final EntityRequest<ChatEntryFindRequest> request) throws ApiException;

    /**
     * Find chat topics.
     *
     * @param request
     *            the request
     * @return the paged payload response
     * @throws ApiException
     *             the api exception
     */
    PagedPayloadResponse<ChatTopic> findChatTopics(FilterRequest request) throws ApiException;

    /**
     * Creates the topic.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<ChatTopic> createTopic(EntityRequest<ChatTopicCreateRequest> request) throws ApiException;

    /**
     * Creates the chat entry.
     *
     * @param request
     *            the request
     * @return the payload response
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<Chat> createChatEntry(EntityRequest<ChatCreateRequest> request) throws ApiException;

}
