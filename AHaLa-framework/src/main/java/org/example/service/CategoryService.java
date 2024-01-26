package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Category;
import org.example.domain.vo.CategoryVo;
import org.example.domain.vo.PageVo;


/**
 * 分类表(Category)表服务接口
 *
 * @author AHaLa
 * @since 2023-12-14 22:22:51
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult<PageVo> pageCategoryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(CategoryVo categoryVo);

    ResponseResult updateCategory(CategoryVo categoryVo);

    ResponseResult getCategory(Long id);

    ResponseResult deleteCategory(Long id);
}
