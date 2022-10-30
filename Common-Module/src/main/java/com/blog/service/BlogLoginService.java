package com.blog.service;

import com.blog.ResponseResult;
import com.blog.domain.entity.User;

public interface BlogLoginService {


    ResponseResult login(User user);
}
