package org.example.controller;


import org.example.domain.ResponseResult;
import org.example.domain.dto.AddRoleDto;
import org.example.domain.dto.AddUserDto;
import org.example.domain.dto.ArticleListDto;
import org.example.domain.dto.UserListDto;
import org.example.domain.entity.User;
import org.example.domain.vo.PageVo;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.pageUserList(pageNum,pageSize,userListDto);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){return userService.addUser(addUserDto);}

    @DeleteMapping("{id}")
    public ResponseResult deleteUser(@PathVariable Long id){return userService.deleteUser(id);}


    @GetMapping("/{id}")
    public ResponseResult getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }
    @PutMapping
    public ResponseResult updateUser(@RequestBody AddUserDto addUserDto){
        return userService.updateUser(addUserDto);
    }

}
