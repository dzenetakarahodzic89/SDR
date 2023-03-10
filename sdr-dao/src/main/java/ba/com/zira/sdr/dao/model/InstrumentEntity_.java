package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InstrumentEntity.class)
public abstract class InstrumentEntity_ {

	public static volatile SingularAttribute<InstrumentEntity, Long> id;
	public static volatile ListAttribute<InstrumentEntity, SongInstrumentEntity> songInstruments;
	public static volatile SingularAttribute<InstrumentEntity, String> name;
	public static volatile SingularAttribute<InstrumentEntity, LocalDateTime> created;
	public static volatile SingularAttribute<InstrumentEntity, String> createdBy;
	public static volatile SingularAttribute<InstrumentEntity, String> information;
	public static volatile SingularAttribute<InstrumentEntity, LocalDateTime> modified;
	public static volatile SingularAttribute<InstrumentEntity, String> modifiedBy;
	public static volatile SingularAttribute<InstrumentEntity, String> status;
	public static volatile SingularAttribute<InstrumentEntity, String> type;
	public static volatile SingularAttribute<InstrumentEntity, String> outlineText;
	public static volatile ListAttribute<NoteSheetEntity, String> noteSheets;
}
