package com.jnshu.student.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.jnshu.student.dao.StudentMapper;
import com.jnshu.student.serviceImpl.RedisService;
import com.jnshu.student.serviceImpl.StudentServiceImpl;
import com.jnshu.student.util.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/*
  短信验证demo
* 调用在测试类
* */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SmsService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    RedisUtil redisUtil = new RedisUtil();
    //普通的类里无法注入RedisBaiseTakes
    //controller
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    static final String accessKeyId = "LTAI4Fqz2c4UYpBwVD7Lopa3";
    static final String accessKeySecret = "hcxjy5IG0heyTEoYKdY4vkza483QH2";
    private static final Logger log = LogManager.getLogger(SmsService.class);

    public JSONObject sendSms(Long mobile_number, int random_code) throws ClientException, IOException {
        JSONObject object=new JSONObject();
        if (studentMapper.checkPhone(mobile_number)!=null&&equals(mobile_number)){
            log.error("该手机号已被注册");
            object.put("code",0);
            object.put("message","该手机号已被注册");
            return object;
        }
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile_number.toString());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("it修真院wang");  //这里需要和你短信模板的名称一致
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_174581069");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        randomCode = random.nextInt(899999)+100000;
        request.setTemplateParam("{\"code\":\""+random_code+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        log.error("----------------短信接口返回的数据----------------");
        log.error("Code=" + sendSmsResponse.getCode());
        log.error("Message=" + sendSmsResponse.getMessage());
        log.error("RequestId=" + sendSmsResponse.getRequestId());
        log.error("BizId=" + sendSmsResponse.getBizId());
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            object.put("code",1);
            object.put("message","短信发送成功");
            return object;
        }else {
            log.error("短信发送出现错误.");
            object.put("code",0);
            object.put("message","短信发送失败");
            return object;
        }
    }


}

