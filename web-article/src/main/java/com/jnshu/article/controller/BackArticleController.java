package com.jnshu.article.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jnshu.article.model.Article;
import com.jnshu.article.service.BackArticleService;

import com.jnshu.article.service.OssService;
import com.jnshu.article.util.DesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-02 15:50
 **/
@RestController
public class BackArticleController {
    @Autowired
    BackArticleService backArticleService;
    @Autowired
    OssService ossService;
    @Autowired
    DesUtil desUtil;
    private static final Logger log = LogManager.getLogger(BackArticleController.class);

    //文章详情
    @RequestMapping(value = "/article",method = RequestMethod.GET)
    @Cacheable(value = "web-article:",key = "#id")
    public JSONObject getA(Long id){
        JSONObject object = new JSONObject();
        object.put("code","1");
        object.put("message","获取单篇成功");
        long time1 = System.currentTimeMillis();
        object.put("data",backArticleService.getArticle(id));
        long time2 = System.currentTimeMillis()-time1;
        log.error("单篇详情耗时:"+time2+"毫秒");
        return object;
    }
    //搜索文章
    @RequestMapping(value = "/article/all",method = RequestMethod.GET)
    @Cacheable(value = "web-articles:",key = "#article.toString()+#page+#size")
    public JSONObject getSomeArticles(Article article ,
                                             @RequestParam(value = "page",defaultValue = "1") int page,
                                             @RequestParam(value = "size",defaultValue = "10") int size)throws Exception{
        log.error("page,size:"+page+size);
        JSONObject object = new JSONObject();
        PageHelper.startPage(page,size);
        ArrayList<Article> articles = backArticleService.getArticles(article);
        object.put("code","1");
        object.put("message","搜索成功");
        PageInfo<Article> pageInfo =new PageInfo<>(articles);
        object.put("data",pageInfo);
        log.error("-----------------pages:"+pageInfo.getPages());
        log.error("-----------------pageSize:"+pageInfo.getPageSize());
        return object;
    }

    //文章新增
    @RequestMapping(value = "/article",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject postA(@RequestBody Article article){
        log.error("新增文章请求: "+article);
        JSONObject object = new JSONObject();
        article.setCreate_at(System.currentTimeMillis());
        article.setUpdate_at(System.currentTimeMillis());
        if (backArticleService.insertArticle(article)){
            object.put("code","1");
            object.put("message","文章新增成功");
            log.error("文章新增成功. title: "+article.getTitle());
        } else{
            object.put("code","0");
            object.put("message","文章新增失败");
            log.error("文章新增失败");
        }
        return object;
    }
    //图片上传
    @RequestMapping(value = "/article/picture",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject postPicture(MultipartFile file) throws IOException {
        log.error("图片上传请求: "+file.getOriginalFilename());
        JSONObject object = new JSONObject();
        String name = file.getOriginalFilename();
        assert name != null;
        String end = name.substring(name.length()-5,name.length());
        String objectName = String.valueOf(System.currentTimeMillis())+end.substring(end.indexOf("."),end.length());
        System.out.println(objectName);
        if (ossService.uploadFile(objectName,file)){
            log.error("图片上传成功: "+objectName);
            object.put("code","1");
            object.put("message","图片上传成功");
            object.put("data","https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/"+objectName);
        }else {
            log.error("图片上传失败");
            object.put("code","0");
            object.put("message","图片上传失败");
            object.put("data",null);
        }
        log.error(object);
        return object;
    }
    //文章编辑
    @RequestMapping(value = "/article",method = RequestMethod.PUT)
    public JSONObject putA(@RequestBody Article article){
        log.error("编辑文章请求: "+article);
        JSONObject object = new JSONObject();
        article.setUpdate_at(System.currentTimeMillis());
        log.error("请求参数: "+article);
        if (backArticleService.updateArticle(article)){
            log.error("文章编辑成功");
            object.put("code","1");
            object.put("message","文章编辑成功");
        } else {
            log.error("文章编辑失败");
            object.put("code","0");
            object.put("message","文章编辑失败");
        }
        log.error(object);
        return object;
    }
    //文章删除
    @RequestMapping(value = "/article",method = RequestMethod.DELETE)
    public JSONObject deleteA(Long id){
        JSONObject object = new JSONObject();
        if (backArticleService.deleteArticle(id)){
            log.error("文章删除成功");
            object.put("code","1");
            object.put("message","删除成功");
        } else {
            log.error("文章删除失败");
            object.put("code","0");
            object.put("message","删除失败");
        }
        log.error(object);
        return object;
    }
    //文章上下架
    @RequestMapping(value = "/article/status",method = RequestMethod.PUT)
    public JSONObject putAU(Long id,Long status){
        log.error("id"+id+".status"+status);
        JSONObject object = new JSONObject();
        if (backArticleService.updateArticleStatus(id, status)){
            backArticleService.articleUpdateAt(id);
            log.error("上架成功");
            object.put("code","1");
            object.put("message","改变上下架状态成功");
        } else {
            log.error("下架成功");
            object.put("code","0");
            object.put("message","改变上下架状态失败");
        }
        return object;
    }
    //测试分页
//    @GetMapping("/test/articles")
//    public void testA(Article article){
//        log.error("进入请求");
//        backArticleService.getArticles(2,5,article);
//    }
}
