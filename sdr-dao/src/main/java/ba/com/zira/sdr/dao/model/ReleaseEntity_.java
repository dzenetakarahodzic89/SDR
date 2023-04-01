package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReleaseEntity.class)
public abstract class ReleaseEntity_ {

    public static volatile SingularAttribute<ReleaseEntity, Long> id;
    public static volatile SingularAttribute<ReleaseEntity, String> gid;
    public static volatile SingularAttribute<ReleaseEntity, String> name;
    public static volatile SingularAttribute<ReleaseEntity, Long> artistCredit;
    public static volatile SingularAttribute<ReleaseEntity, Long> releaseGroup;
    public static volatile SingularAttribute<ReleaseEntity, Long> status;
    public static volatile SingularAttribute<ReleaseEntity, Long> packaging;
    public static volatile SingularAttribute<ReleaseEntity, Long> language;
    public static volatile SingularAttribute<ReleaseEntity, Long> script;
    public static volatile SingularAttribute<ReleaseEntity, String> barcode;
    public static volatile SingularAttribute<ReleaseEntity, String> comment;
    public static volatile SingularAttribute<ReleaseEntity, Long> editsPending;
    public static volatile SingularAttribute<ReleaseEntity, Long> quality;
    public static volatile SingularAttribute<ReleaseEntity, LocalDateTime> lastUpdated;
    public static volatile ListAttribute<ReleaseEntity, ReleaseCountryEntity> releaseCountries;

}
