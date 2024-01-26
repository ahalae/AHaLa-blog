package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddRoleDto;
import org.example.domain.dto.RoleListDto;
import org.example.domain.entity.Role;
import org.example.domain.entity.RoleMenu;
import org.example.domain.entity.Tag;
import org.example.domain.vo.PageVo;
import org.example.mapper.RoleMapper;
import org.example.service.RoleMenuService;
import org.example.service.RoleService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author AHaLa
 * @since 2024-01-15 01:33:56
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否为管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys=new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //如果不是，则查询当前用户具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult<PageVo> pageRoleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        //分页查询
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(roleListDto.getRoleName()),Role::getRoleName,roleListDto.getRoleName());
        queryWrapper.eq(StringUtils.hasText(roleListDto.getStatus()),Role::getStatus,roleListDto.getStatus());

        Page<Role> page =new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //封装数据
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleListDto roleListDto) {
        Role role = getById(roleListDto.getRoleId());
        role.setStatus(roleListDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        for (String menuId : addRoleDto.getMenuIds()) {
            RoleMenu roleMenu=new RoleMenu(role.getId(),Long.parseLong(menuId));
            roleMenuService.save(roleMenu);
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Integer id) {
        Role role = getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    public ResponseResult updateRole(AddRoleDto addRoleDto) {

        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,addRoleDto.getId());
        roleMenuService.remove(queryWrapper);
        for (String menuId : addRoleDto.getMenuIds()) {
            RoleMenu roleMenu=new RoleMenu(addRoleDto.getId(),Long.parseLong(menuId));
            roleMenuService.save(roleMenu);
        }

        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        updateById(role);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Integer id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(queryWrapper);
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = list(queryWrapper);
        return ResponseResult.okResult(roles);
    }
}
