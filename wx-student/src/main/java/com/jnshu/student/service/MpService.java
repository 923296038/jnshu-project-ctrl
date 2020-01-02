package com.jnshu.student.service;


import com.alibaba.fastjson.JSONObject;
import com.jnshu.student.dao.StudentMapper;
import com.jnshu.student.serviceImpl.StudentServiceImpl;
import com.jnshu.student.util.RedisUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
* 邮箱验证demo
* */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class MpService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private static int mpError = 0;
    private static final Logger log = LogManager.getLogger(MpService.class);
    private static final String apiUser = "923296038_test_Md4LJd";
    private static final String apiKey = "W8lykjy2mUcmG548";
    Random random = new Random();

    public JSONObject sendMail(String email_number,int random_code) throws IOException {
        log.error("-----sendMail:"+email_number+random_code);
        JSONObject object = new JSONObject();
        if (studentMapper.checkEmail(email_number)!=null&&equals(email_number)){
            log.error("邮箱已被注册.");
            object.put("code",0);
            object.put("message","邮箱已被注册");
            return object;
        }
        final String url = "http://api.sendcloud.net/apiv2/mail/send";
        //填写你自己的APIUser和APIKey
        /*Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream("D:\\my_code\\task6\\src\\main\\resources\\mp.properties"));
        properties.load(inputStream);*/
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httPost = new HttpPost(url);
        List params = new ArrayList();
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("from", "wang@oZtkgRU7E8aRUdtLLH4omdYT13qAVqpi.sendcloud.org"));
        params.add(new BasicNameValuePair("fromName", "jnshu.ctrl.sendMail"));
        params.add(new BasicNameValuePair("to",email_number));
        params.add(new BasicNameValuePair("subject", "[求学大作战]邮箱验证"));
        /*此处随机生成验证码,附在邮件中发送,然后用户输入,判断输入与发送的是否一致.*/
        params.add(new BasicNameValuePair("html", "您的验证码是 "+random_code+",该验证码5分钟内有效,不要告诉他人."));
        httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httPost);
        httPost.releaseConnection();
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 验证码写入缓存
            stringRedisTemplate.opsForValue().set(String.valueOf(email_number), String.valueOf(random_code),60*5, TimeUnit.SECONDS);
            String result = EntityUtils.toString(response.getEntity());
            log.error("_________邮件发送结果_________"+result);
            object.put("code",1);
            object.put("message","发送成功");
            return object;
        } else {
            log.error("mp发生错误.");
            object.put("code",0);
            object.put("message","邮件发送出错");
            return object;
        }
    }

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        final int random_code =random.nextInt(899999)+100000;
        MpService mpService =new MpService();
        mpService.sendMail("923296038@qq.com",random_code);
        //写入缓存

    }

}
