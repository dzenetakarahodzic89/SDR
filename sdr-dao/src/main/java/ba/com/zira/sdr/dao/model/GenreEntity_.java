package ba.com.zira.sdr.dao.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GenreEntity.class)
public abstract class GenreEntity_ {

    public static volatile SingularAttribute<GenreEntity, Long> id;
    public static volatile SingularAttribute<GenreEntity, String> name;
    public static volatile SingularAttribute<GenreEntity, GenreEntity> mainGenre;

}
