package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InstrumentEntity.class)
public abstract class SongInstrumentEntity_ {

	public static volatile SingularAttribute<SongInstrumentEntity, Long> id;
	public static volatile SingularAttribute<SongInstrumentEntity, SongEntity> song;
	public static volatile SingularAttribute<SongInstrumentEntity, InstrumentEntity> instrument;
	public static volatile SingularAttribute<SongInstrumentEntity, LocalDateTime> created;
	public static volatile SingularAttribute<SongInstrumentEntity, String> createdBy;
	public static volatile SingularAttribute<SongInstrumentEntity, LocalDateTime> modified;
	public static volatile SingularAttribute<SongInstrumentEntity, String> modifiedBy;
	public static volatile SingularAttribute<SongInstrumentEntity, String> name;
	public static volatile SingularAttribute<InstrumentEntity, Long> instrumentId;
	public static volatile SingularAttribute<SongInstrumentEntity, PersonEntity> person;
	public static volatile SingularAttribute<SongEntity, Long> songId;
	public static volatile SingularAttribute<SongInstrumentEntity, String> outlineText;
}
