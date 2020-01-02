package com.jnshu.article.serviceImpl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jnshu.article.dao.BackArticleMapper;
import com.jnshu.article.model.Article;
import com.jnshu.article.service.BackArticleService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-30 18:19
 **/
@Service
public class BackArticleServiceImpl implements BackArticleService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BackArticleMapper BackArticleMapper;
    @Autowired
    RedisTemplate redisTemplate;
    private static final Logger log = LogManager.getLogger(BackArticleServiceImpl.class);

    @Override
    public ArrayList<Article> getArticles(Article article) {
        log.error("service.getArticles--------------------------------------");
        ArrayList<Article> articles = BackArticleMapper.getArticles(article);
        log.error("查询文章数量: "+articles.size());
        return articles;
    }

    @Override
    public Article getArticle(Long id) {
        return BackArticleMapper.getArticle(id);
    }

    @Override
    public boolean articleUpdateAt(Long id) {
        Long update_at = System.currentTimeMillis();
        return BackArticleMapper.articleUpdateAt(update_at,id);
    }
    @Override
    public boolean insertArticle(Article article) {
        return BackArticleMapper.insertArticle(article);
    }

    @Override
    public boolean updateArticle(Article article) {
        return BackArticleMapper.updateArticle(article);
    }

    @Override
    public boolean deleteArticle(Long id) {
        return BackArticleMapper.deleteArticle(id);
    }

    @Override
    public boolean updateArticleStatus(Long id, Long status) {
        return BackArticleMapper.updateArticleStatus(id, status);
    }


}
