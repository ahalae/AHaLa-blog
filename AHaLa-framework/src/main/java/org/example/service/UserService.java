package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddUserDto;
import org.example.domain.dto.UserListDto;
import org.example.domain.entity.User;
import org.example.domain.vo.PageVo;


/**
 * 用户表(User)表服务接口
 *
 * @author AHaLa
 * @since 2024-01-08 04:45:11
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult<PageVo> pageUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUser(Long id);

    ResponseResult updateUser(AddUserDto addUserDto);
}
