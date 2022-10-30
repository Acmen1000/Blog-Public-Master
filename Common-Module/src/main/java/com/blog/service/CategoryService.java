package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.ResponseResult;
import com.blog.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author 码农三号
 * @since 2022-10-19 18:47:49
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}

