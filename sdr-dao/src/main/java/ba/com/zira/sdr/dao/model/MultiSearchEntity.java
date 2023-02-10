package ba.com.zira.sdr.dao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Table(name = "sdr_multi_search")
@NamedQuery(name = "MultiSearchEntity.find", query = "SELECT ms FROM MultiSearchEntity ms")
public class MultiSearchEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String name;
    private String type;

}