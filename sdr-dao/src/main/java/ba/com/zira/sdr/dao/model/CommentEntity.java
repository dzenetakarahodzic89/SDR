package ba.com.zira.sdr.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the "sat_comment" database table.
 *
 */
@Entity
@Table(name = "sat_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "CommentEntity.findAll", query = "SELECT c FROM CommentEntity c")
public class CommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SAT_COMMENT_ID_GENERATOR", sequenceName = "SAT_COMMENT_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAT_COMMENT_ID_GENERATOR")
    @Column(name = "id")
    private Long id;

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

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "object_type")
    private String objectType;

    @Column(name = "status")
    private String status;

    @Column(name = "user_code")
    private String userCode;

}