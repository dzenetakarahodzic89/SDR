package ba.com.zira.sdr.dao;

import org.springframework.stereotype.Repository;

import ba.com.zira.commons.dao.AbstractDAO;
import ba.com.zira.sdr.dao.model.SongEntity;

@Repository
public class SongDAO extends AbstractDAO<SongEntity, Long> {
}
