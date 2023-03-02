package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PlaylistEntity.class)
public abstract class PlaylistEntity_ {

    public static volatile SingularAttribute<PlaylistEntity, Long> id;
    public static volatile SingularAttribute<PlaylistEntity, LocalDateTime> created;
    public static volatile SingularAttribute<PlaylistEntity, String> createdBy;
    public static volatile SingularAttribute<PlaylistEntity, String> information;
    public static volatile SingularAttribute<PlaylistEntity, LocalDateTime> modified;
    public static volatile SingularAttribute<PlaylistEntity, String> modifiedBy;
    public static volatile SingularAttribute<PlaylistEntity, String> name;
    public static volatile SingularAttribute<PlaylistEntity, Long> numberOfPlays;
    public static volatile SingularAttribute<PlaylistEntity, Long> numberOfShares;
    public static volatile SingularAttribute<PlaylistEntity, String> status;
    public static volatile SingularAttribute<PlaylistEntity, Long> totalPlaytime;
    public static volatile SingularAttribute<PlaylistEntity, String> userCode;
    public static volatile SingularAttribute<PlaylistEntity, String> outlineText;
    public static volatile ListAttribute<PlaylistEntity, SongPlaylistEntity> songPlaylists;

}
