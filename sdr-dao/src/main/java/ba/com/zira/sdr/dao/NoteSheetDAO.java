package ba.com.zira.sdr.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;

@Repository
public class NoteSheetDAO extends AbstractDAO<NoteSheetEntity, Long> {

    public List<NoteSheetEntity> getNoteSheetsByIds(final List<Long> noteSheetIds) {
        if (noteSheetIds == null || noteSheetIds.isEmpty()) {
            return Collections.emptyList();
        }

        String hql = "SELECT n FROM NoteSheetEntity n WHERE n.id IN (:noteSheetIds)";
        TypedQuery<NoteSheetEntity> query = entityManager.createQuery(hql, NoteSheetEntity.class);
        query.setParameter("noteSheetIds", noteSheetIds);

        return query.getResultList();
    }
}
