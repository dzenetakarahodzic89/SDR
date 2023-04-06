package ba.com.zira.sdr.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.sdr.api.model.chat.Chat;
import ba.com.zira.sdr.api.model.chat.ChatTopic;
import ba.com.zira.sdr.dao.model.ChatEntity;

@Repository
public class ChatDAO extends AbstractDAO<ChatEntity, Long> {

    public PagedData<ChatTopic> findTopics(int page, int pageSize) {
        var idHql = "SELECT DISTINCT c.chatId FROM ChatEntity c GROUP BY c.chatId";

        var countHql = "SELECT COUNT(DISTINCT c.chatId) FROM ChatEntity c";
        var numberOfRecords = entityManager.createQuery(countHql, Long.class).getSingleResult().intValue();

        PagedData<ChatTopic> pagedData = new PagedData<>();

        pagedData.setNumberOfPages((int) Math.ceil((float) numberOfRecords / pageSize));
        pagedData.setNumberOfRecords(numberOfRecords);

        if (page > pagedData.getNumberOfPages()) {
            page = 1;
        }

        int firstResult = (page - 1) * pageSize;
        int maxResults = pageSize;

        var chatIds = entityManager.createQuery(idHql, String.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

        pagedData.setPage(page);
        pagedData.setRecordsPerPage(pageSize);

        var chatTopics = new ArrayList<ChatTopic>();

        chatIds.stream().forEach(cId -> {
            var hql = "SELECT DISTINCT new ba.com.zira.sdr.api.model.chat.ChatTopic(c.chatId,c.topic,c.userCode,MIN(c.created))"
                    + " FROM ChatEntity c WHERE c.chatId=:cId GROUP BY c.chatId,c.topic,c.userCode ORDER BY MIN(c.created) ASC";

            chatTopics.add(entityManager.createQuery(hql, ChatTopic.class).setParameter("cId", cId).setMaxResults(1).getSingleResult());
        });

        chatTopics.sort((x, y) -> x.getCreated().isAfter(y.getCreated()) ? -1 : 1);

        pagedData.setRecords(chatTopics);

        return pagedData;
    }

    public PagedData<Chat> findChatEntries(String chatId, int page, int pageSize) {
        var countHql = "SELECT COUNT(DISTINCT c.id) FROM ChatEntity c WHERE c.chatId=:chatId";
        var numberOfRecords = entityManager.createQuery(countHql, Long.class).setParameter("chatId", chatId).getSingleResult().intValue();

        PagedData<Chat> pagedData = new PagedData<>();

        pagedData.setNumberOfPages((int) Math.ceil((float) numberOfRecords / pageSize));
        pagedData.setNumberOfRecords(numberOfRecords);

        if (page > pagedData.getNumberOfPages()) {
            page = 1;
        }

        int firstResult = (page - 1) * pageSize;
        int maxResults = pageSize;

        pagedData.setPage(page);
        pagedData.setRecordsPerPage(pageSize);

        var hql = "SELECT new ba.com.zira.sdr.api.model.chat.Chat(c.id,c.userCode,c.created,c.content,c.chatId,c.topic) "
                + "FROM ChatEntity c where c.chatId=:chatId ORDER BY c.created ASC";

        pagedData.setRecords(entityManager.createQuery(hql, Chat.class).setParameter("chatId", chatId).setFirstResult(firstResult)
                .setMaxResults(maxResults).getResultList());

        return pagedData;

    }

    public ChatTopic findByChatId(String chatId) {

        var hql = "SELECT new ba.com.zira.sdr.api.model.chat.ChatTopic(c.chatId,c.topic,c.userCode,c.created)"
                + " FROM ChatEntity c WHERE c.chatId=:chatId";
        var q = entityManager.createQuery(hql, ChatTopic.class).setParameter("chatId", chatId).setMaxResults(1);

        try {
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public ChatTopic findByTopicName(String name) {
        var hql = "SELECT new ba.com.zira.sdr.api.model.chat.ChatTopic(c.chatId,c.topic,c.userCode,c.created)"
                + " FROM ChatEntity c WHERE c.topic=:name";
        var q = entityManager.createQuery(hql, ChatTopic.class).setParameter("name", name).setMaxResults(1);

        try {
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
