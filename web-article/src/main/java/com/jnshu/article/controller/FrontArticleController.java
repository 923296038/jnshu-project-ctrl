package com.jnshu.article.controller;

import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.annotations.JsonAdapter;
import com.jnshu.article.ArticleApplication;
import com.jnshu.article.model.Article;
import com.jnshu.article.service.BackArticleService;
import com.jnshu.article.service.FrontArticleService;
import com.jnshu.article.util.DesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-02 16:30
 **/
@RestController
public class FrontArticleController {
    @Autowired
    FrontArticleService frontArticleService;
    @Autowired
    DesUtil desUtil;
    private static final Logger log = LogManager.getLogger(FrontArticleController.class);
    /*
    文章前台
    */
    //前台文章详情,token默认空√
    @GetMapping("/article/front")
    @Cacheable(value = "wx-article:",key = "#id")
    public JSONObject getArticle(Long id, @RequestParam(value = "token",defaultValue = "")String token) throws Exception {
        JSONObject object = new JSONObject();
        //token不合法的情况
        if (token.length()>10){
            if (!frontArticleService.verifyToken(token)){
                object.put("code","0");
                object.put("message","无效的token");
                return object;
            }
        }
        object.put("code","1");
        object.put("message","请求成功");
        object.put("data",frontArticleService.getArticle(token,id));
        return object;
    }
    //前台搜索文章,按标题√
    @GetMapping("/article/some")
    @Cacheable(value = "wx-articles:",key = "#title+#page+#size")
    public JSONObject getArticleSome(String title,@RequestParam(value = "token",defaultValue = "")String token,
                                     @RequestParam(value = "page",defaultValue = "1") int page,
                                     @RequestParam(value = "size",defaultValue = "10") int size) throws Exception {
        JSONObject object = new JSONObject();
        //token不合法的情况
        if (token.length()>10){
            if (!frontArticleService.verifyToken(token)){
                object.put("code","0");
                object.put("message","无效的token");
                return object;
            }
        }
        object.put("code","1");
        object.put("message","搜索成功");
        PageHelper.startPage(page,size);
        ArrayList<Article> articles = frontArticleService.queryArticles(token,title);
        object.put("data",new PageInfo<Article>(articles));
        return object;
    }
    //前台文章列表√
    @GetMapping("/article/all/front")
    @Cacheable(value = "wx-articles",key = "#token+#page+#size")
    public JSONObject getAllArticles2(@RequestParam(value = "token",defaultValue = "")String token,
                                      @RequestParam(value = "page",defaultValue = "1") int page,
                                      @RequestParam(value = "size",defaultValue = "10") int size) throws Exception {
        JSONObject object = new JSONObject();
        //token不合法的情况
        if (token.length()>10){
            if (!frontArticleService.verifyToken(token)){
                object.put("code","0");
                object.put("message","无效的token");
                return object;
            }
        }
        PageHelper.startPage(page,size);
        ArrayList<Article> articles = frontArticleService.getFrontArticles(token);
        object.put("code","1");
        object.put("message","搜索成功");
        object.put("data",new PageInfo<Article>(articles));
        return object;
    }
    //文章点赞
    @RequestMapping(value = "/article/like",method = RequestMethod.PUT)
    public JSONObject putALike(String token,Long aid) throws Exception {
        JSONObject object = new JSONObject();
        object.put("code","1");
        if (frontArticleService.putArticleLike(token, aid))
            object.put("message","点赞成功");
        else object.put("message","点赞失败.");
        return object;
    }
    //文章收藏
    @RequestMapping(value = "/article/collection",method = RequestMethod.PUT)
    public JSONObject putACol(String token,Long aid) throws Exception {
        JSONObject object = new JSONObject();
        object.put("code","1");
        if (frontArticleService.putArticleColletcion(token, aid))
            object.put("message","收藏成功");
        else object.put("message","收藏失败");
        return object;
    }

}
