package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sat_mb_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "EventEntity.findAll", query = "SELECT a FROM EventEntity a")
public class EventEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_MB_EVENT_ID_GENERATOR", sequenceName = "SAT_EVENT_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_MB_EVENT_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "gid")
    private String gid;

    @Column(name = "name")
    private String name;

    @Column(name = "begin_date_year")
    private Long beginDateYear;

    @Column(name = "begin_date_month")
    private Long beginDateMonth;

    @Column(name = "begin_date_day")
    private Long beginDateDay;

    @Column(name = "end_date_year")
    private Long endDateYear;

    @Column(name = "end_date_month")
    private Long endDateMonth;

    @Column(name = "end_date_day")
    private Long endDateDay;

    @Column(name = "time")
    private Time time;

    @Column(name = "type")
    private Long type;

    @Column(name = "cancelled")
    private String cancelled;

    @Column(name = "setlist")
    private String setlist;

    @Column(name = "comment")
    private String comment;

    @Column(name = "edits_pending")
    private Long editsPending;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "ended")
    private String ended;
}
