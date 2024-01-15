package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author AHaLa
 * @since 2024-01-15 01:24:20
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);
}
