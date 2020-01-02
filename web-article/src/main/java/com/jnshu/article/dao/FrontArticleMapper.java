package com.jnshu.article.dao;

import com.jnshu.article.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-03 10:19
 **/
@Mapper
public interface FrontArticleMapper {
    //前台文章列表,登录
    @Select("select article.* , article_like.sid as like_sid, article_collection.sid as collection_sid from article \n" +
            "left join article_like on article_like.aid=article.id " +
            "and article_like.sid=(select id from student where openId=#{openId})\n" +
            "left join article_collection on article_collection.aid=article.id \n" +
            "and article_collection.sid=(select id from student where openId=#{openId}) " +
            "where article.status=1 order by update_at desc")
    ArrayList<Article> getAllArticles(String openId);
    //前台文章列表,未登录
    @Select("select * from article where status=1 order by update_at desc")
    ArrayList<Article> getAllArticlesUn();
    //文章详情,登录
    @Select("select article.* , article_like.sid as like_sid, article_collection.sid as collection_sid from article \n" +
            "left join article_like on article_like.aid=article.id " +
            "and article_like.sid=(select id from student where openId=#{openId})\n" +
            "left join article_collection on article_collection.aid=article.id \n" +
            "and article_collection.sid=(select id from student where openId=#{openId})\n" +
            "where article.id = #{id}")
    Article getArticle(String openId,Long id);
    //文章详情,未登录
    @Select("select * from article where id = #{id} and status=1")
    Article getArticleUn(Long id);
    //根据title搜索文章
    @Select("select article.* , article_like.sid as like_sid, article_collection.sid as collection_sid from article \n" +
            "left join article_like on article_like.aid=article.id " +
            "and article_like.sid=(select id from student where openId=#{openId})\n" +
            "left join article_collection on article_collection.aid=article.id \n" +
            "and article_collection.sid=(select id from student where openId=#{openId})\n" +
            "where article.title like CONCAT(CONCAT('%',#{title},'%')) and article.status=1 order by update_at desc")
    ArrayList<Article> queryArticles(String title,String openId);
    //搜索文章,未登录
    @Select("select * from article where title like CONCAT(CONCAT('%',#{title},'%')) and status=1 order by update_at desc")
    ArrayList<Article> queryArticlesUn(String title);
    //点赞数+1
    @Update("update article set number_of_likes=number_of_likes+1 where id = #{aid}")
    boolean putArticleLike(Long aid);
    //收藏数+1
    @Update("update article set number_of_collections=number_of_collections+1 where id = #{aid}")
    boolean putArticleCollection(Long aid);
    //收藏表中记录
    @Insert("insert into article_collection (aid,sid) values (#{aid},(select id from student where openId = #{openId}))")
    boolean collectArticle(String openId, Long aid);
    //点赞表中记录
    @Insert("insert into article_like (aid,sid) values (#{aid},(select id from student where openId = #{openId}))")
    boolean likeArticle(String openId,Long aid);
    //点赞数-1
    @Update("update article set number_of_likes=number_of_likes-1 where id = #{aid}")
    boolean numberOfLikesMinus(Long aid);
    //收藏数-1
    @Update("update article set number_of_collections=number_of_collections-1 where id = #{aid}")
    boolean numberOfCollectionsMinus(Long aid);
    //点赞表中删除
    @Delete("delete from article_like where aid=#{aid} and sid=(select id from student where openId=#{openId})")
    boolean cancelLike(String openId,Long aid);
    //收藏表中删除
    @Delete("delete from article_collection where aid=#{aid} and sid=(select id from student where openId=#{openId})")
    boolean cancelCollect(String openId,Long aid);
    //是否点赞过
    @Select("select count(*) from article_like where aid=#{aid} and " +
            "sid=(select id from student where openId=#{openId})")
    int likeOrNot(String openId,Long aid);
    //是否收藏过
    @Select("select count(*) from article_collection where aid=#{aid} and " +
            "sid=(select id from student where openId=#{openId})")
    int collectOrNot(String openId,Long aid);
    //是否有存在这个openId
    @Select("select count(openId) from student where openId=#{openId}")
    int countByOpenId(String openId);
}
