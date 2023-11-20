package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LabelEntity.class)
public abstract class LabelEntity_ {

    public static volatile SingularAttribute<LabelEntity, Long> id;
    public static volatile SingularAttribute<LabelEntity, String> name;
    public static volatile ListAttribute<LabelEntity, SongArtistEntity> songArtists;
    public static volatile SingularAttribute<LabelEntity, PersonEntity> founder;
    public static volatile SingularAttribute<LabelEntity, LocalDateTime> modified;
    public static volatile SingularAttribute<LabelEntity, LocalDateTime> created;
    public static volatile SingularAttribute<LabelEntity, LocalDateTime> foundingDate;
    public static volatile SingularAttribute<LabelEntity, String> information;
    public static volatile SingularAttribute<LabelEntity, String> outlineText;
}
