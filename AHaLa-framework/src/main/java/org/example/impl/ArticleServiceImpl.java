package org.example.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.entity.Article;
import org.example.mapper.ArticleMapper;
import org.example.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
