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

/**
 * The persistent class for the "sat_news_articles" database table.
 *
 */
@Entity
@Table(name = "sat_news_articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "NewsArticlesEntity.findAll", query = "SELECT nae FROM NewsArticlesEntity nae")
public class NewsArticlesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "SAT_NEWS_ARTICLES_ID_GENERATOR", sequenceName = "SAT_NEWS_ARTICLES_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_NEWS_ARTICLES_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "link")
    private String link;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;
}
