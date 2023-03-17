package ba.com.zira.sdr.core.impl;

import org.springframework.stereotype.Service;

import ba.com.zira.sdr.dao.NewsArticlesDAO;
import ba.com.zira.sdr.dao.RssFeedDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NewsArticlesServiceImpl {
    NewsArticlesDAO newsArticlesDAO;
    RssFeedDAO rssFeedDAO;
}
