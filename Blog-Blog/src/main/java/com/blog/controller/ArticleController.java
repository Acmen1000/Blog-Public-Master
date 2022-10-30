package com.blog.controller;


import com.blog.ResponseResult;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章，封装ResponseResult返回
       //可以直接使用 articleService.list();，然后封装条件，但是不建议这样写
        //调用业务层方法，业务逻辑写到Service层
        return articleService.hotArticleListService();

    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        //查询所有文章，进行分页处理，封装ResponseResult返回
        return articleService.articleList(pageNum, pageSize,categoryId);

    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable ("id")Long id){
        //根据Id查询详细文章
        return articleService.getArticleDetail(id);

    }

}
