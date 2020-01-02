package com.jnshu.student.dao;

import com.jnshu.student.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Mapper
public interface StudentMapper {
    //select 用户信息
    @Select("select * from student where 1=1 and openId=#{openId}")
    Student selectStudent(String openId);
    //insert student
    @Insert("insert into student (openId,name,image,create_at,update_at) " +
            "values (#{openId},#{name},#{image},#{create_at},#{update_at})")
    Boolean insertStudent(Student student);
    //update 信息
    @Update("<script>update student <trim prefix=\"set\" suffixOverrides=\",\"> <if test=\"name!=null\">name=#{name},</if><if test=\"image!=null\">image=#{image},</if>\n" +
            "<if test=\"grade!=0\">grade=#{grade},</if>update_at=#{update_at} where openId=#{openId}</trim></script>")
    Boolean updateStudent(Student student);
    //查article-collection
    @Select("select article.* from article,article_collection where article.id=article_collection.aid and article_collection.sid = (select id from student where openId = #{openId})")
    ArrayList<Article> articleCollection(String openId);
    //查video-collection
    @Select("select video.* from video,video_collection where video.id=video_collection.vid and video_collection.collection_sid = (select id from student where openId = #{openId})")
    ArrayList<Video> videoCollection(String openId);
    //update phone
    @Update("update student set phone=#{phone} where openId=#{openId}")
    Boolean updatePhone(Long phone,String openId);
    //update email
    @Update("update student set email=#{email} where openId=#{openId}")
    Boolean updateEmail(String email,String openId);
    //是否有存在这个openId
    @Select("select count(openId) from student where openId=#{openId}")
    int countByOpenId(String openId);
    //根据openId查student.status
    @Select("select status from student where openId=#{openId}")
    int queryStatus(String openId);
    //手机号查重
    @Select("select phone from student where phone=#{phone}")
    Long checkPhone(Long phone);
    //邮箱查重
    @Select("select email from student where email=#{email}")
    String checkEmail(String email);
    //查询豆子
    @Select("select beans_amount from student where openId=#{openId}")
    int queryBean(String openId);
    //增加20个豆子
    @Update("update student set beans_amount = beans_amount+20 where openId=#{openId}")
    Boolean getTweentyBeans(String openId);
}
