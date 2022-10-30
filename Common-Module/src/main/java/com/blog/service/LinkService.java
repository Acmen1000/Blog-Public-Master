package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.ResponseResult;
import com.blog.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author 码农三号
 * @since 2022-10-24 18:14:35
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

