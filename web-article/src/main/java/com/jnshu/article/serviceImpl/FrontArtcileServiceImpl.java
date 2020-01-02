package com.jnshu.article.serviceImpl;

import com.jnshu.article.dao.BackArticleMapper;
import com.jnshu.article.dao.FrontArticleMapper;
import com.jnshu.article.model.Article;
import com.jnshu.article.service.FrontArticleService;
import com.jnshu.article.util.DesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-03 10:22
 **/
@Service
public class FrontArtcileServiceImpl implements FrontArticleService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    FrontArticleMapper frontArticleMapper;
    @Autowired
    DesUtil desUtil;
    private static final Logger log = LogManager.getLogger(FrontArtcileServiceImpl.class);
    //文章点赞
    @Override
    public Boolean putArticleLike(String token,Long aid) throws Exception {
        log.error("点赞请求: "+token+aid);
        String openId = desUtil.getOpenId(token);
        if (frontArticleMapper.likeOrNot(openId,aid)==1){
            //取消点赞
            boolean result1= frontArticleMapper.numberOfLikesMinus(aid);
            boolean result2= frontArticleMapper.cancelLike(openId,aid);
            log.error("取消点赞: "+result1+result2);
            return result1&&result2;
        }else {
            //点赞
            boolean result3= frontArticleMapper.putArticleLike(aid);
            boolean result4= frontArticleMapper.likeArticle(desUtil.getOpenId(token),aid);
            log.error("点赞: "+result3+result4);
            return result3&&result4;
        }
    }
    //文章收藏
    @Override
    public Boolean putArticleColletcion(String token, Long aid) throws Exception {
        log.error("收藏请求: "+token+aid);
        String openId = desUtil.getOpenId(token);
        if (frontArticleMapper.collectOrNot(openId,aid)==1){
            //取消收藏
            boolean result1= frontArticleMapper.numberOfCollectionsMinus(aid);
            boolean result2= frontArticleMapper.cancelCollect(openId,aid);
            log.error("取消收藏: "+result1+result2);
            return result1&&result2;
        }else {
            //收藏
            boolean result3= frontArticleMapper.putArticleCollection(aid);
            boolean result4= frontArticleMapper.collectArticle(openId,aid);
            log.error("收藏: "+result3+result4);
            return result3&&result4;
        }
    }
    @Override
    public Boolean verifyToken(String token) throws Exception {
        Boolean result = frontArticleMapper.countByOpenId(desUtil.getOpenId(token))==1;
        log.error("token校验结果: "+result);
        return result;
    }
    //文章详情
    @Override
    public Article getArticle(String token, Long id) throws Exception {
        log.error("文章详情请求: "+token+id);
        if (token.length()>10) {
            String openId = desUtil.getOpenId(token);
            Article article = frontArticleMapper.getArticle(openId,id);
            log.error("文章详情结果: "+article);
            return article;
        }else {
            Article article = frontArticleMapper.getArticleUn(id);
            log.error("文章详情结果: "+article);
            return article;
        }
    }
    //搜索文章
    @Override
    public ArrayList<Article> queryArticles(String token, String title) throws Exception {
        log.error("搜索文章请求: "+token+title);
        if (token.length()>10) {
            String openId = desUtil.getOpenId(token);
            ArrayList<Article> articles = frontArticleMapper.queryArticles(title,openId);
            log.error("搜索文章结果: "+articles.size());
            return articles;
        }else {
            ArrayList<Article> articles = frontArticleMapper.queryArticlesUn(title);
            log.error("搜索文章结果: "+articles.size());
            return articles;
        }
    }
    //文章列表
    @Override
    public ArrayList<Article> getFrontArticles(String token) throws Exception {
        log.error("getFrontArticles: "+token);
        if (token.length()>10) {
            String openId = desUtil.getOpenId(token);
            ArrayList<Article> articles = frontArticleMapper.getAllArticles(openId);
            log.error("前台文章列表: "+articles.size());
            return articles;
        }else {
            ArrayList<Article> articles = frontArticleMapper.getAllArticlesUn();
            log.error("前台文章列表: "+articles.size());
            return articles;
        }
    }
}
