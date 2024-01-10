package org.example.job;

import org.example.domain.entity.Article;
import org.example.service.ArticleService;
import org.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCount {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateViewCount(){
        //获取redis浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库
        articleService.updateBatchById(articles);
    }
}
