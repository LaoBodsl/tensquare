package com.tensquare.reach.service;

import com.tensquare.reach.dao.ArticleDao;
import com.tensquare.reach.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public void add(Article article){
        articleDao.save(article);
    }

    public Page<Article> findBykey(String key, int page, int size){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return articleDao.findByTitleOrContentLike(key, key,pageRequest);

    }


}
