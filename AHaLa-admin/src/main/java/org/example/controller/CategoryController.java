package org.example.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.example.domain.ResponseResult;
import org.example.domain.dto.UserListDto;
import org.example.domain.entity.Category;
import org.example.domain.vo.CategoryVo;
import org.example.domain.vo.ExcelCategoryVo;
import org.example.domain.vo.PageVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.service.CategoryService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }


    @PreAuthorize("@ps.hasPermission('cotent:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {

        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, String name,String status){
        return categoryService.pageCategoryList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.addCategory(categoryVo);
    }

    @GetMapping("{id}")
    public ResponseResult getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id);
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.updateCategory(categoryVo);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
