package org.example.controller;

import org.example.domain.ResponseResult;
import org.example.domain.dto.ArticleListDto;
import org.example.domain.dto.TagListDto;
import org.example.domain.entity.Menu;
import org.example.domain.vo.PageVo;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public ResponseResult list(String status,String menuName){
        return ResponseResult.okResult(menuService.menuList(status,menuName));
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable("id") Integer id){
        return menuService.getMenu(id);
    }
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable("id") Integer id){
        return menuService.deleteMenu(id);
    }

}
