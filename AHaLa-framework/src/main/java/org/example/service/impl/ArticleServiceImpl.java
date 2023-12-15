package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Article;
import org.example.domain.vo.HotArticleVo;
import org.example.mapper.ArticleMapper;
import org.example.service.ArticleService;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL );
        //按浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 分页查询
        Page<Article> page=new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles=page.getRecords();

        //Bean拷贝

//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo=new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);


        return ResponseResult.okResult(articleVos);
    }
}
