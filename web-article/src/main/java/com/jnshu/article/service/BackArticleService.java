package com.jnshu.article.service;

import com.github.pagehelper.PageInfo;
import com.jnshu.article.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-30 14:22
 **/

public interface BackArticleService {
    //获取文章列表
//    ArrayList<Article> getAllArticles();
    //更改文章更新时间
    boolean articleUpdateAt(Long id);
    //文章详情
    Article getArticle(Long id);
    //新增
    boolean insertArticle(Article article);
    //编辑
    boolean updateArticle(Article article);
    //删除
    boolean deleteArticle(Long id);
    //上下架
    boolean updateArticleStatus(Long id, Long status);
    //动态查询
    ArrayList<Article> getArticles(Article article);
}
