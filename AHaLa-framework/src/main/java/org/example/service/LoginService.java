package org.example.service;

import org.example.domain.ResponseResult;
import org.example.domain.entity.User;

public interface LoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
