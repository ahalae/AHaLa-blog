package org.example.service.impl;

import org.example.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    public boolean hasPermission(String permission){
        //如果是超级管理员，返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则获取当前用户权限列表并判断
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
