package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Article;
import org.example.domain.entity.Category;
import org.example.domain.vo.categoryVo;
import org.example.mapper.CategoryMapper;
import org.example.service.ArticleService;
import org.example.service.CategoryService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author AHaLa
 * @since 2023-12-14 22:22:52
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表已发布文章
        LambdaQueryWrapper<Article> articleWrapper =new LambdaQueryWrapper<Article>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList=articleService.list(articleWrapper);
        //获取文章分类id
        Set<Long> catagoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(catagoryIds);

        categories=categories.stream()
                .filter(catagory->SystemConstants.STATUS_NORMAL.equals(catagory.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<categoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, categoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
