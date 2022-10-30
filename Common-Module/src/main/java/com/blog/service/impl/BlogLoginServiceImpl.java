package com.blog.service.impl;

import com.blog.ResponseResult;
import com.blog.Utils.BeanCopyUtils;
import com.blog.Utils.JwtUtil;
import com.blog.Utils.RedisCache;
import com.blog.domain.entity.LoginUser;
import com.blog.domain.entity.User;
import com.blog.domain.vo.BlogUserLoginVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());


        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误！");
        }
        //获取userId生成token
        LoginUser loginUser=(LoginUser) authenticate.getPrincipal();
       String userId=loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);


        //把用户信息存入redis
        redisCache.setCacheObject("bloglogn"+userId,loginUser);


        //把token和userinfo封装返回
        UserInfoVo userInfoVo=BeanCopyUtils.copyBean(loginUser.getUser(),UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo=new BlogUserLoginVo(jwt,userInfoVo);
        //返回登录成功的用户信息
        return ResponseResult.okResult(blogUserLoginVo);

    }
}
