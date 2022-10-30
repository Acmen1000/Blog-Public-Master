package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.Utils.BeanCopyUtils;
import com.blog.ResponseResult;
import com.blog.constants.SystemConstants;
import com.blog.domain.entity.Link;
import com.blog.domain.vo.linkVo;
import com.blog.mapper.LinkMapper;
import com.blog.service.LinkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author 码农三号
 * @since 2022-10-24 18:14:35
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus,SystemConstants.Link_STATUS_NORMAL);
        List<Link> links=list(lambdaQueryWrapper);
        //转换Vo
        List<linkVo> linkVos = BeanCopyUtils.copyBeanList(links, linkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}

