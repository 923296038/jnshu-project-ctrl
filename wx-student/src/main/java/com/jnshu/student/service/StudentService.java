package com.jnshu.student.service;

import com.alibaba.fastjson.JSONObject;
import com.jnshu.student.model.Article;
import com.jnshu.student.model.ArticleCollection;
import com.jnshu.student.model.Student;
import com.jnshu.student.model.Video;

import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-05 15:44
 **/
public interface StudentService {
    //获取用户信息
    Student getInfo(String token) throws Exception;
    //新增用户
    Boolean createStudentOpenId(String openId) throws Exception;
    Boolean createStudent(JSONObject userInfoJSON);
    //编辑信息
    Boolean changeInfo(Student student) throws Exception;
    //文章收藏列表
    ArrayList<Article> getArticleCollection(String token) throws Exception;
    //视频收藏列表
    ArrayList<Video> getVideoCollection(String token) throws Exception;
    //绑定手机,邮箱
    Boolean bindPhone(String token,Long phone,int random_code) throws Exception;
    Boolean bindEmail(String token,String email,int random_code) throws Exception;
    //校验token
    Boolean verifyToken(String token) throws Exception;
    //检验openId
    Boolean verifyOpenId(String openId);
    //检查账号状态
    Boolean checkStudentStatus(String openId);
    //邮箱手机查重
    Boolean phoneExistOrNot(Long phone);
    Boolean emailExistOrNot(String email);
    //绑定成功,获得20逆袭豆
    Boolean bindingSuccess(String openId);
}
