package com.jnshu.student.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.jnshu.student.model.Article;
import com.jnshu.student.model.Student;
import com.jnshu.student.model.Video;
import com.jnshu.student.service.MpService;
import com.jnshu.student.service.SmsService;
import com.jnshu.student.util.ObjectGetter;
import jdk.nashorn.internal.scripts.JS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-07 14:06
 **/
@RestController
public class StudentController_mock {
    @Autowired
    ObjectGetter objectGetter;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    MpService mpService;
    @Autowired
    SmsService smsService;
    Random random = new Random();
    /*学生证模块*/



    /*
    测试
    */
    @GetMapping("/test/redis")
    public String testRedis(){
        stringRedisTemplate.opsForValue().set("20191207","123456");
        System.out.println(stringRedisTemplate.opsForValue().get("20191207"));
        return stringRedisTemplate.opsForValue().get("20191207");
    }
}
