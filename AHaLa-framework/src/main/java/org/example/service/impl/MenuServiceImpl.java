package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Strings;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Menu;
import org.example.domain.entity.RoleMenu;
import org.example.domain.vo.RoleMenuTreeSelectVo;
import org.example.domain.vo.TreeSelectVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.exception.SystemException;
import org.example.mapper.MenuMapper;
import org.example.mapper.RoleMenuMapper;
import org.example.service.MenuService;
import org.example.service.RoleMenuService;
import org.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author AHaLa
 * @since 2024-01-15 01:24:20
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper=new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限

        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是返回所有符合要求的menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //如果不是则返回用户所具有的menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建menu tree
        //先找出第一层菜单，并找到其子菜单设置到children中
        List<Menu> menuTree = buildMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public List<Menu> menuList(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Strings.hasText(status),Menu::getStatus,status);
        queryWrapper.eq(Strings.hasText(menuName),Menu::getMenuName,menuName);
        queryWrapper.orderBy(true,true,Menu::getId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        return menus;
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Integer id) {
        Menu menu = getBaseMapper().selectById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getParentId().equals(menu.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Integer id) {
        List<Menu> menuTree = buildMenuTree(list(),id);
        if(!menuTree.isEmpty()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<TreeSelectVo> treeSelect() {
        List<Menu> menuTree = list();
        List<TreeSelectVo> treeSelectVos = menuTree.stream()
                .map(o -> new TreeSelectVo(o.getId(), o.getMenuName(), o.getParentId(),null))
                .collect(Collectors.toList());
        treeSelectVos=buildTreeSelectVoTree(treeSelectVos,0L);
        return treeSelectVos;
    }

    @Override
    public ResponseResult roleMenuTreeSelect(Integer id) {
        //获得菜单树
        List<TreeSelectVo> treeSelectVos = treeSelect();
        //获得权限列表
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        List<String> checkedKeys = roleMenus.stream()
                .map(o -> o.getMenuId().toString())
                .collect(Collectors.toList());
        RoleMenuTreeSelectVo roleMenuTreeSelectVo = new RoleMenuTreeSelectVo(treeSelectVos, checkedKeys);
        return ResponseResult.okResult(roleMenuTreeSelectVo);
    }

    private List<TreeSelectVo> buildTreeSelectVoTree(List<TreeSelectVo> treeSelectVos, long parentId) {
        List<TreeSelectVo> treeSelectVoTree = treeSelectVos.stream()
                .filter(treeSelectVo -> treeSelectVo.getParentId().equals(parentId))
                .map(treeSelectVo -> treeSelectVo.setChildren(getTreeSelectVoChildren(treeSelectVo,treeSelectVos)))
                .collect(Collectors.toList());

        return treeSelectVoTree;
    }

    private List<TreeSelectVo> getTreeSelectVoChildren(TreeSelectVo treeSelectVo, List<TreeSelectVo> treeSelectVos) {
        List<TreeSelectVo> childrenList = treeSelectVos.stream()
                .filter(m -> m.getParentId().equals(treeSelectVo.getId()))
                .map(m-> m.setChildren(getTreeSelectVoChildren(m,treeSelectVos)))
                .collect(Collectors.toList());
        return childrenList;
    }

    private List<Menu> buildMenuTree(List<Menu> menus, long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m-> m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}
