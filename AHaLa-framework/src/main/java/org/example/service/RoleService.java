package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author AHaLa
 * @since 2024-01-15 01:33:56
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
