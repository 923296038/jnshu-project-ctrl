package com.jnshu.student.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.jnshu.student.dao.StudentMapper;
import com.jnshu.student.model.Article;
import com.jnshu.student.model.Student;
import com.jnshu.student.model.Video;
import com.jnshu.student.service.StudentService;
import com.jnshu.student.util.DesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Random;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-05 17:41
 **/
@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LogManager.getLogger(StudentServiceImpl.class);
    @Resource
    StudentMapper studentMapper;
    @Resource
    DesUtil desUtil;
    Random random = new Random();
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Student getInfo(String token) throws Exception {
        return studentMapper.selectStudent(desUtil.getOpenId(token));
    }

    @Override
    public Boolean createStudentOpenId(String openId) throws Exception {
        Student student = new Student();
        student.setName("学生"+random.nextInt(100000));
        student.setImage("http://www.cqsccw.com/upload/Article/201411/22/2014112210064980375.jpg");
        student.setCreate_at(System.currentTimeMillis());
        student.setUpdate_at(System.currentTimeMillis());
        student.setOpenId(openId);
        return studentMapper.insertStudent(student);
    }

    @Override
    public Boolean createStudent(JSONObject userInfoJSON) {
        log.error("用户敏感信息: "+userInfoJSON);
        Student student = new Student();
        student.setOpenId((String) userInfoJSON.get("openId"));
        student.setName((String) userInfoJSON.get("nickName"));
        student.setImage((String) userInfoJSON.get("avatarUrl"));
        student.setArea((String) userInfoJSON.get("province")+(String) userInfoJSON.get("city"));
        student.setCreate_at(System.currentTimeMillis());
        student.setUpdate_at(System.currentTimeMillis());
        log.error("新增学生: "+student);
        return studentMapper.insertStudent(student);
    }

    @Override
    public Boolean changeInfo(Student student) throws Exception {
        student.setOpenId(desUtil.getOpenId(student.getToken()));
        return studentMapper.updateStudent(student);
    }

    @Override
    public ArrayList<Article> getArticleCollection(String token) throws Exception {
        log.error("openId: "+desUtil.getOpenId(token));
        ArrayList<Article> articles = studentMapper.articleCollection(desUtil.getOpenId(token));
        log.error("文章收藏数量: "+articles.size());
        return articles;
    }

    @Override
    public ArrayList<Video> getVideoCollection(String token) throws Exception {
        log.error("openId: "+desUtil.getOpenId(token));
        ArrayList<Video> videos = studentMapper.videoCollection(desUtil.getOpenId(token));
        log.error("视频收藏数量: "+ videos.size());
        return videos;
    }

    @Override
    public Boolean bindPhone(String token, Long phone,int random_code) throws Exception {
        log.error("传入的: "+token+","+phone+","+random_code);
        log.error("验证码校验"+random_code+">> "+stringRedisTemplate.opsForValue().get(phone.toString()));
        if (String.valueOf(random_code).equals(stringRedisTemplate.opsForValue().get(phone.toString()))){
            return studentMapper.updatePhone(phone,desUtil.getOpenId(token));

        }else {
            return false;
        }
    }

    @Override
    public Boolean bindEmail(String token, String email,int random_code) throws Exception {
        log.error("传入的: "+token+","+email+","+random_code);
        if (String.valueOf(random_code).equals(stringRedisTemplate.opsForValue().get(email))){
            return studentMapper.updateEmail(email,desUtil.getOpenId(token));
        }else {
            return false;
        }
    }

    @Override
    public Boolean verifyToken(String token) throws Exception {
        Boolean result = studentMapper.countByOpenId(desUtil.getOpenId(token))==1;
        log.error("token校验结果: "+result);
        return result;
    }

    @Override
    public Boolean verifyOpenId(String openId) {
        return studentMapper.countByOpenId(openId)==1;
    }

    @Override
    public Boolean checkStudentStatus(String openId) {
        log.error("studentService.checkStatus ,openId:"+openId);
        if (studentMapper.countByOpenId(openId)==1){
            int status = studentMapper.queryStatus(openId);
            log.error("查出该账户状态:"+status);
            if(status>0&status==1){
                return true;
            }else
                return false;
        }else {
            log.error("该openId未注册");
            return false;
        }
    }
    @Override
    public Boolean phoneExistOrNot(Long phone) {
        //返回true表示手机号已被注册
        return studentMapper.checkPhone(phone)==phone;
    }

    @Override
    public Boolean emailExistOrNot(String email) {
        //返回true表示邮箱已被注册
        return studentMapper.checkEmail(email).equals(email);
    }

    @Override
    public Boolean bindingSuccess(String openId) {
        log.error("studentService.bindingSuccess: "+openId);
        if (studentMapper.countByOpenId(openId)==1){
            return studentMapper.getTweentyBeans(openId);
        }else{
            log.error("openId不存在");
            return false;
        }
    }
}
