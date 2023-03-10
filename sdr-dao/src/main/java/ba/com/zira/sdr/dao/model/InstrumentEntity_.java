package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InstrumentEntity.class)
public abstract class InstrumentEntity_ {

    public static volatile SingularAttribute<InstrumentEntity, Long> id;
    public static volatile ListAttribute<SongEntity, SongArtistEntity> songInstruments;
    public static volatile SingularAttribute<SongEntity, String> name;
}
