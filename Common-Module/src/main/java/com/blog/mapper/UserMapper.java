package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.entity.User;
import org.springframework.stereotype.Service;


/**
 * 用户表(User)表数据库访问层
 *
 * @author 码农三号
 * @since 2022-10-25 16:14:11
 */
@Service
public interface UserMapper extends BaseMapper<User> {

}

