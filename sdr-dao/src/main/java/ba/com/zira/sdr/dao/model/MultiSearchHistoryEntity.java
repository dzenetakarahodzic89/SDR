package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Table(name = "sat_multi_search_history")
@NamedQuery(name = "MultiSearchHistoryEntity.find", query = "SELECT smsh FROM MultiSearchHistoryEntity smsh")
public class MultiSearchHistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "refresh_time")
    private LocalDateTime refreshTime;

    @Column(name = "rows_before")
    private Long rowsBefore;

    @Column(name = "rows_after")
    private Long rowsAfter;

    @Column(name = "data_structure")
    private String dataStructure;

}