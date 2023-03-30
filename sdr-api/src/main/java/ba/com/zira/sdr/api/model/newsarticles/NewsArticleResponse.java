package ba.com.zira.sdr.api.model.newsarticles;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Properties of an news articles response")
@AllArgsConstructor
public class NewsArticleResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String imageUrl;
    private String link;
    private String content;
}
