package com.jnshu.student.util;

import com.jnshu.student.model.Article;
import com.jnshu.student.model.Student;
import com.jnshu.student.model.Video;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-07 14:12
 **/
@Component
public class ObjectGetter {
    Random random = new Random();
    Random randomTime = new Random();
    public Student getStudent(){
        Student student = new Student();
        student.setName("陈振华");
        student.setImage("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        student.setGrade(2);
        student.setBeans_amount(129);
        student.setPhone(13999999999L);
        student.setEmail("111111111@qq.com");
        return student;
    }
    public Article getArticle(){
        Article article =new Article();
        article.setTitle("刘平老师的Angular教学");
        article.setImage("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        article.setAuthor("刘平老师");
        article.setIntroduction("看了这篇文章,年薪立马百万");
        article.setNumber_of_likes((long) random.nextInt(10000));
        article.setNumber_of_collections((long) random.nextInt(7000));
        article.setUpdate_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        return article;
    }
    public Video getVideo(){
        Video video = new Video();
        video.setTeacher("刘平老师");
        video.setTeacher_image("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        video.setNumber_of_likes(random.nextInt(3000));
        video.setNumber_of_collections(random.nextInt(3000));
        video.setIntroduction("");
        video.setVideo_image("https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/1547.jpg");
        video.setTitle("刘平老师的教学视频");
        video.setVideo_time(200);
        video.setVideo_url("http://v.pptv.com/show/2z5QzzedDUuuLLA.html?rcc_id=web_2056&vfm=bdvppzq&frp=v.baidu.com%2Ftvplay_intro%2Fbrowse&kwid=29312");
        video.setCollect_at(System.currentTimeMillis()-randomTime.nextInt(20000000));
        return video;
    }
}
