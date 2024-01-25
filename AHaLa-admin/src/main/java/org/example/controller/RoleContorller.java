package org.example.controller;

import org.example.domain.ResponseResult;
import org.example.domain.dto.AddRoleDto;
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

    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.addRole(addRoleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable("id") Integer id){
        return roleService.getRole(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.updateRole(addRoleDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteRole(@PathVariable Integer id){return roleService.deleteRole(id);}


}
