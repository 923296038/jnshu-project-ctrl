package com.jnshu.article.util;

import com.jnshu.article.model.*;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @description:生成具体实例
 * @authoer:Wang
 * @create_at:2019-11-29 14:31
 **/
@Component
public class InitializeClass {
    Random random = new Random(100);
    Random randomTime = new Random(20000000);
    public Article getBArticle(){
        Article article = new Article();
        article.setId((long) random.nextInt(100));
        article.setTitle("这是一篇文章");
        article.setImage("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        article.setType("R18");
        article.setAuthor("作者");
        article.setContent("1.获取个人信息(头像,昵称,豆子)\n" +
                "\n" +
                "2.我的收藏列表 查student-collection表\n" +
                "\n" +
                "3.账号与绑定设置 判断是否绑定\n" +
                "\n" +
                "4.编辑个人信息\n" +
                "\n" +
                "5.绑定手机/邮箱 ");

        article.setIntroduction("看了这篇文章,清北你也能上");
        article.setStatus(1L);
        article.setNumber_of_likes(1611L);
        article.setNumber_of_collections(112L);
        return article;
    }
    public Article getFArticle(){
        Article article =new Article();
        article.setTitle("这是一篇文章");
        article.setImage("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        article.setAuthor("作者");
        article.setContent("1.获取个人信息(头像,昵称,豆子)\n" +
                "\n" +
                "2.我的收藏列表 查student-collection表\n" +
                "\n" +
                "3.账号与绑定设置 判断是否绑定\n" +
                "\n" +
                "4.编辑个人信息\n" +
                "\n" +
                "5.绑定手机/邮箱 ");
        article.setIntroduction("看了这篇文章,清北你也能上");
        article.setNumber_of_likes((long) random.nextInt(10000));
        article.setNumber_of_collections((long) random.nextInt(7000));
        article.setUpdate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        return article;
    }
    public Student getFStudent(){
        Student student = new Student();
        student.setName("普通学生");
        student.setImage("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        student.setGrade(10);
        student.setBeans_amount(random.nextInt(500));
        if (random.nextInt(2)==1) {
            student.setPhone(13972102240L);
        }else student.setPhone(0L);
        if (random.nextInt(2)==1){
            student.setEmail("923296038@qq.com");
        }else student.setEmail(null);

        return student;
    }
    public Video getFVideo(){
        Video video = new Video();
        video.setTeacher("一位老师");
        video.setTeacher_image("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        video.setNumber_of_likes(random.nextInt(3000));
        video.setNumber_of_collections(random.nextInt(3000));
        video.setIntroduction("");
        video.setVideo_image("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        video.setTitle("这是一个视频");
        video.setVideo_time(200);
        video.setVideo_url("http://v.pptv.com/show/2z5QzzedDUuuLLA.html?rcc_id=web_2056&vfm=bdvppzq&frp=v.baidu.com%2Ftvplay_intro%2Fbrowse&kwid=29312");
        video.setCollect_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        return video;
    }
    public Account getAccount(){
        Account account = new Account();
        account.setId((long) random.nextInt(100));
        account.setUsername("一位用户");
        account.setRole("管理员");
        account.setCreate_by("一位管理员");
        account.setCreate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        account.setUpdate_by("另一位管理员");
        account.setUpdate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        return account;
    }
    public Role getRole(){
        Role role = new Role();
        role.setId((long) random.nextInt(100));
        role.setRole("管理员");
        role.setCreate_by("你");
        role.setCreate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        role.setUpdate_by("你");
        role.setUpdate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        return role;
    }
    public Module getModule(){
        Module module = new Module();
        module.setId((long) random.nextInt(100));
        module.setName("一个模块");
        module.setModule_url("一个url");
        module.setParent_id(0L);
        module.setUpdate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        module.setUpdate_by("张飞");
        module.setCreate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        module.setCreate_by("管理员");
        return module;
    }
    public Student getStudent(){
        Student student =new Student();
        student.setId((long) random.nextInt(100));
        student.setName("小红帽");
        student.setEmail("abcd@qq.com");
        student.setPhone(123456L);
        student.setBeans_amount(random.nextInt(1000));
        student.setGrade(4);
        student.setArea_province("青青");
        student.setArea_city("草原");
        student.setAccount_status(1);
        return student;
    }
}
