package org.example.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Category;
import org.example.domain.vo.CategoryVo;
import org.example.domain.vo.ExcelCategoryVo;
import org.example.domain.vo.LinkVo;
import org.example.domain.vo.PageVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.service.CategoryService;
import org.example.service.LinkService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    LinkService linkService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, String name, String status){
        return linkService.pageLinkList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkVo linkVo){
        return linkService.addLink(linkVo);
    }

    @GetMapping("{id}")
    public ResponseResult getLink(@PathVariable("id") Long id){
        return linkService.getLink(id);
    }
    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkVo linkVo){
        return linkService.updateLink(linkVo);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return linkService.deleteLink(id);
    }
}
