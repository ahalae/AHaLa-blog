package org.example.controller;

import org.example.domain.ResponseResult;
import org.example.domain.dto.RoleListDto;
import org.example.domain.dto.TagListDto;
import org.example.domain.vo.PageVo;
import org.example.service.MenuService;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleContorller {
    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, RoleListDto roleListDto){
        return roleService.pageRoleList(pageNum,pageSize,roleListDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleListDto roleListDto){
        return roleService.changeStatus(roleListDto);
    }
}
