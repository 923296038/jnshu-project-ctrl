package com.jnshu.article.service;

import com.jnshu.article.model.Article;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface FrontArticleService {
    //文章点赞
    Boolean putArticleLike(String token,Long aid) throws Exception;
    //文章收藏
    Boolean putArticleColletcion(String token,Long aid) throws Exception;
    //检验token
    Boolean verifyToken(String token) throws Exception;
    //文章详情
    Article getArticle(String token,Long id) throws Exception;
    //搜索文章
    ArrayList<Article> queryArticles(String token,String title) throws Exception;
    //文章列表
    ArrayList<Article> getFrontArticles(String token) throws Exception;
}
