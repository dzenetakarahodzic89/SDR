package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AlbumEntity.class)
public abstract class SongInstrumentEntity_ {

    public static volatile SingularAttribute<SongInstrumentEntity, Long> id;
    public static volatile SingularAttribute<SongInstrumentEntity, SongEntity> song;
    public static volatile SingularAttribute<SongInstrumentEntity, InstrumentEntity> instrument;

}
