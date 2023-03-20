package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SongScoresEntity.class)
public class SongScoresEntity_ {
    public static volatile SingularAttribute<SongScoresEntity, Long> id;
    public static volatile SingularAttribute<SongScoresEntity, GenreEntity> genre;
    public static volatile SingularAttribute<SongScoresEntity, Long> songId;
}
