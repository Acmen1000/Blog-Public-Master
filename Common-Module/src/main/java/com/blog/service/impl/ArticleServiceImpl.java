package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.Utils.BeanCopyUtils;
import com.blog.ResponseResult;
import com.blog.constants.SystemConstants;
import com.blog.domain.entity.Article;
import com.blog.domain.entity.Category;
import com.blog.domain.vo.ArticleDeateilVo;
import com.blog.domain.vo.ArticleListVo;
import com.blog.domain.vo.HotArticleVo;
import com.blog.domain.vo.PageVo;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleListService() {
        //查询热门文章，封装ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();

        //非草稿文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        //按浏览量来排名
        queryWrapper.orderByDesc(Article::getViewCount);


        //分页一页查询10条
        Page<Article> page=new Page(1,10);
        page(page,queryWrapper);
        //将查到的数据放在List中
        List<Article> articleList=page.getRecords();
        //bean拷贝
        List<HotArticleVo> articleVos= BeanCopyUtils.copyBeanList(articleList,HotArticleVo.class);
//        //bean拷贝
//        List<HotArticleVo> articleVos=new ArrayList<>();
//        for(Article article :articleList){
//            HotArticleVo vo=new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        //返回拷贝
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article=getById(id);
        //转换Vo
        ArticleDeateilVo articleDeateilVo = BeanCopyUtils.copyBean(article, ArticleDeateilVo.class);
        //根据分类Id查询分类名
        Long categoryId =articleDeateilVo.getCategoryId();
        Category category=categoryService.getById(categoryId);
        if(category!=null){
            articleDeateilVo.setCategoryName(category.getName());

        }

        //封装响应对象返回
        return ResponseResult.okResult(articleDeateilVo);
    }

}
