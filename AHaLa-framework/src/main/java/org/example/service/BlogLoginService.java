package org.example.service;

import org.example.domain.ResponseResult;
import org.example.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
