package ba.com.zira.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.model.chat.Chat;
import ba.com.zira.sdr.api.model.chat.ChatCreateRequest;
import ba.com.zira.sdr.api.model.chat.ChatTopic;
import ba.com.zira.sdr.api.model.chat.ChatTopicCreateRequest;
import ba.com.zira.sdr.dao.model.ChatEntity;

@Component
@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatEntity chatDtoToEntity(ChatCreateRequest chatCreateRequest);

    ChatEntity topicDtoToEntity(ChatTopicCreateRequest chatTopicCreateRequest);

    Chat entityToChatDto(ChatEntity chatEntity);

    ChatTopic entityToTopicDto(ChatEntity chatEntity);

    List<Chat> entitiesToChatDtos(List<ChatEntity> chatEntities);

    List<ChatTopic> entitiesToTopicDtos(List<ChatEntity> chatEntities);
}
