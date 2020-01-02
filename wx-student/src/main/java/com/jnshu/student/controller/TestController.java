package com.jnshu.student.controller;

import com.jnshu.student.service.SmsService;
import com.jnshu.student.service.StudentService;
import com.jnshu.student.service.TestService;
import com.jnshu.student.serviceImpl.StudentServiceImpl;
import com.jnshu.student.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-10 17:36
 **/
@RestController
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    SmsService smsService;
    @Resource
    RedisUtil redisUtil;
    @GetMapping("/test/error")
    public Long testError(Long phone){
        return redisUtil.errorPlus(phone+"Error");
    }
    @GetMapping("/test/shc")
    public Boolean stillHavaChance(Long phone){
        return redisUtil.stillHavaChance(phone+"Error");
    }

}
