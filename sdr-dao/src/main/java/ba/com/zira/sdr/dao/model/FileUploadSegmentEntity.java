package ba.com.zira.sdr.dao.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sat_file_upload_segment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = "FileUploadSegmentEntity.findAll", query = "SELECT c FROM FileUploadSegmentEntity c")
public class FileUploadSegmentEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "file_segment")
    private Long fileSegment;

    @Column(name = "file_segment_total")
    private Long fileSegmentTotal;

    @Column(name = "file_segment_content")
    private String fileSegmentContent;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    // bi-directional many-to-one association to MediaEntity
    @ManyToOne
    @JoinColumn(name = "media_id")
    private MediaEntity media;

}
