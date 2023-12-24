package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Article;
import org.example.domain.entity.Category;
import org.example.domain.vo.ArticleListVo;
import org.example.domain.vo.HotArticleVo;
import org.example.domain.vo.PageVo;
import org.example.mapper.ArticleMapper;
import org.example.service.ArticleService;
import org.example.service.CategoryService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Lazy
    @Autowired
    private CategoryService categoryService;
    
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

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        // 如果有categoryId 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        // 状态是正式发布
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop降序 置顶操作
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryId
        List<Article> articles = page.getRecords();
        //查询categoryName
//        for (Article article : articles) {
//            Category category = categoryService.getById((article.getCategoryId()));
//            article.setCategoryName(category.getName());
//        }

        articles=articles.stream()
                .map(article ->article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());



        //封装查询
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);



        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
