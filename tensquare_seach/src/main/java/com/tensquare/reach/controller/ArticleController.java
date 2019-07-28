package com.tensquare.reach.controller;


import com.tensquare.reach.pojo.Article;
import com.tensquare.reach.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleService.add(article);
        return new Result(true, StatusCode.OK,"增加成功");
    }

    @RequestMapping(value = "/search/{key}/{page}/{size}", method = RequestMethod.GET)
    public Result findBykey(@PathVariable String key, @PathVariable int page, @PathVariable int size){
        Page<Article> pagedata = articleService.findBykey(key,page,size);
        return new Result(true,StatusCode.OK,"查询成功", new PageResult<Article>(pagedata.getTotalElements(),pagedata.getContent()));

    }
}
