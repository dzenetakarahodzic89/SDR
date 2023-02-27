package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SongPlaylistEntity.class)
public class SongPlaylistEntity_ {
    public static volatile SingularAttribute<SongPlaylistEntity, SongEntity> song;
    public static volatile SingularAttribute<SongPlaylistEntity, PlaylistEntity> playlist;

}
