package com.jnshu.article.dao;


import com.jnshu.article.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface BackArticleMapper {
    //所有文章
    @Select("select * from article order by update_at desc")
    ArrayList<Article> getAllArticles();
    //文章详情
    @Select("select * from article where id = #{id}")
    Article getArticle(Long id);
    //文章新增
    @Insert("insert into article (title,type,image,author,introduction,content,create_at,update_at) " +
            "values (#{title},#{type},#{image},#{author},#{introduction},#{content},#{create_at},#{update_at})")
    boolean insertArticle(Article article);
    //文章编辑
    @Update("update article set title=#{title},type=#{type},image=#{image},author=#{author},introduction=#{introduction},content=#{content},update_at=#{update_at}" +
            " where id = #{id}")
    boolean updateArticle(Article article);
    //删除
    @Delete("delete article where id = #{id}")
    boolean deleteArticle(Long id);
    //上下架
    @Update("update article set status = #{status} where id = #{id}")
    boolean updateArticleStatus(Long id, Long status);
    //动态查询
    @Select("<script>select * from article where 1=1 <if test=\"title!=null and title!=''\"> and title like CONCAT(CONCAT('%',#{title},'%')) </if> \n" +
            "<if test=\"status>=0 and status!=null\"> and status=#{status} </if> \n" +
            "<if test=\"type!=null and type!=''\"> and type=#{type}\t</if> \n" +
            "<if test=\"author!=null and author!=''\"> and author like CONCAT(CONCAT('%',#{author},'%')) </if> \n" +
            "<if test=\"likes_min>=0 and likes_min!=null\">and number_of_likes &gt;= #{likes_min} </if> \n" +
            "<if test=\"likes_max>=0 and likes_max!=null\"> and number_of_likes &lt;= #{likes_max} </if> \n" +
            "<if test=\"collections_min>=0 and collections_min!=null\"> and number_of_collections &gt;= #{collections_min} </if> " +
            "<if test=\"collections_max>=0 and collections_max!=null\"> and number_of_collections &lt;= #{collections_max} </if> " +
            "order by update_at desc</script>")
    ArrayList<Article> getArticles(Article article);
    @Update("update article set update_at = #{update_at} where id = #{id}")
    boolean articleUpdateAt(Long update_at,Long id);
}
