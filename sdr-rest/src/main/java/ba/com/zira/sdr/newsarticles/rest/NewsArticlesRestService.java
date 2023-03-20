package ba.com.zira.sdr.newsarticles.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.NewsArticlesService;
import ba.com.zira.sdr.api.model.newsarticles.NewsArticleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "news-articles")
@RestController
@RequestMapping(value = "news-articles")
public class NewsArticlesRestService {
    @Autowired
    private NewsArticlesService newsArticlesService;

    @Operation(summary = "Get fresh blog content")
    @GetMapping(value = "get-fresh")
    public ListPayloadResponse<NewsArticleResponse> getFreshContent() throws ApiException {
        return newsArticlesService.getFreshContent(new EmptyRequest());
    }
}
