package ba.com.zira.sdr.dao.model;

import java.io.Serializable;
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
@Table(name = "sat_rss_feed")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "RssFeedEntity.findAll", query = "SELECT rfe FROM RssFeedEntity rfe")
public class RssFeedEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "SAT_RSS_FEED_ID_GENERATOR", sequenceName = "SAT_RSS_FEED_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_RSS_FEED_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "adapter")
    private String adapter;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;
}
