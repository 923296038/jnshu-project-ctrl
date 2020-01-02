package com.jnshu.student.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.jnshu.student.dao.StudentMapper;
import com.jnshu.student.model.Article;
import com.jnshu.student.model.Student;
import com.jnshu.student.model.Video;
import com.jnshu.student.service.MpService;
import com.jnshu.student.service.OssService;
import com.jnshu.student.service.SmsService;
import com.jnshu.student.service.StudentService;
import com.jnshu.student.serviceImpl.StudentServiceImpl;
import com.jnshu.student.util.*;
import jdk.nashorn.internal.parser.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-05 15:55
 **/
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
public class StudentController {
    @Resource
    OssService ossService;
    @Resource
    DesUtil desUtil;
    @Resource
    RedisUtil redisUtil;
    @Resource
    MpService mpService;
    @Resource
    SmsService smsService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    ObjectGetter objectGetter;
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    StudentMapper studentMapper;
    private Random random = new Random();
    private static final Logger log = LogManager.getLogger(StudentController.class);
    private String AppId ="wxf6f94063054d3e89";
    private String AppSecret="b0b8563b253cbfc493286fe007a39782";
    private String grant_type="authorization_code";

    //1.登录
    @RequestMapping(value = "/student/login",method = RequestMethod.POST)
    public JSONObject decodeUserInfo(String code,String iv,String encryptedData) throws Exception {
        log.error("请求登录: "+code+iv+encryptedData);
        JSONObject object = new JSONObject();
        String time_in_format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分").format(new Date(System.currentTimeMillis()));
        String params = "appid=" + AppId + "&secret=" + AppSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        String jscode2session = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject json = JSONObject.parseObject(jscode2session);
        String openId = (String) json.get("openid");
        log.error("解析出的openId: "+openId);
        //若student.status=0则返回被冻结
        /*if (! studentService.checkStudentStatus(openId)){
            log.error(openId+",该用户已被冻结");
            object.put("code",2);
            object.put("message","该用户已被冻结");
        }*/
        if (studentService.verifyOpenId(openId)){
            log.error("该用户已注册:"+openId);
            object.put("code","1");
            object.put("message","请求成功,用户登录成功");
            object.put("token",desUtil.encrypt(openId+"/"+time_in_format));
        }else {
            //注册
            log.error("正在注册用户");
            String session_key = (String) json.get("session_key");
            //解密
            String userInfo = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            JSONObject userInfoJSON = JSONObject.parseObject(userInfo);
            log.error("用户数据: "+userInfoJSON);
            if (studentService.createStudent(userInfoJSON)) {
                object.put("code","1");
                object.put("message","请求成功,用户注册成功");
                //签发token
                object.put("token",desUtil.encrypt(openId+"/"+time_in_format));
            }else {
                object.put("code","0");
                object.put("message","用户注册失败");
            }
        }
        return object;
    }

    /*@PostMapping("/student/register")
    public JSONObject studentRegister(String code,String iv,String encryptedData){
        JSONObject object = new JSONObject();
        String params = "appid=" + AppId + "&secret=" + AppSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        JSONObject json = JSONObject.parseObject(HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params));
        log.error("解析出的openId: "+(String) json.get("openid"));
        if (studentService.verifyOpenId((String) json.get("openid"))){
            try {
                JSONObject userInfoJSON = JSONObject.parseObject(AesCbcUtil.decrypt(encryptedData, (String) json.get("session_key"), iv, "UTF-8"));
                //解析敏感数据,insert
                studentService.createStudent(userInfoJSON);
                object.put("code","1");
                object.put("message","注册成功");
                object.put("token",desUtil.encrypt((String) json.get("openid")+"/"+new SimpleDateFormat("yyyy年MM月dd日HH时mm分").format(new Date(System.currentTimeMillis()))));
                return object;
            }catch (Exception e){
                log.error("敏感信息解密失败");
                e.printStackTrace();
            }
        }else {
            log.error("用户已存在,无需注册");
        }
        object.put("code","0");
        object.put("message","注册失败");
        return object;
    }*/

    /*
    学生证
    */
    //用户信息√
    @GetMapping("/student/info")
    public JSONObject studentI(String token) throws Exception {
        JSONObject object = new JSONObject();
        if (token.length()>1) {
            object.put("code", "1");
            object.put("message", "获取成功");
            Student student = studentService.getInfo(token);
            object.put("data", student);
            log.error("查询用户信息结果: "+student);
        }else {
            object.put("code",0);
            object.put("message","失败,无效的token");
        }
        return object;
    }
    //编辑信息√
    @PutMapping("/student/info")
    public JSONObject putStudentI(Student student) throws Exception {
        JSONObject object = new JSONObject();
        student.setUpdate_at(System.currentTimeMillis());
        log.error("请求编辑信息: "+student);
        //校验无误
        if (studentService.changeInfo(student)){
            object.put("code","1");
            object.put("message","编辑成功");
            log.error("编辑信息成功");
            return object;
        }else {
            object.put("code","0");
            object.put("message","编辑失败");
            log.error("编辑信息失败!!");
            return object;
        }

    }
    //文章收藏列表√
    @GetMapping("/student/articleCollection")
    public JSONObject studentArticleC(String token) throws Exception {
        JSONObject object = new JSONObject();
        object.put("code",1);
        object.put("message","请求成功");
        ArrayList<Article> articles = studentService.getArticleCollection(token);
        log.error("文章收藏数: "+articles.size());
        object.put("data",articles);
        return object;
    }
    //视频收藏列表√
    @GetMapping("/student/videoCollection")
    public JSONObject studentVideoC(String token) throws Exception {
        JSONObject object = new JSONObject();
        object.put("code",1);
        object.put("message","请求成功");
        ArrayList<Video> videos = studentService.getVideoCollection(token);
        log.error("视频收藏数: "+videos.size());
        object.put("data",videos);
        return object;

    }
    //绑定手机√
    @PutMapping("/student/binding/phone")
    public JSONObject studentBinding(String token,Long phone,int check_code) throws Exception {
        log.error("请求绑定手机: "+token+phone+check_code);
        JSONObject object = new JSONObject();
        if (studentService.verifyToken(token)){
            //如果redis value>3则失败
            if (!redisUtil.stillHavaChance(phone+"Error")){
                object.put("code",0);
                object.put("message","失败次数过多.明天再试");
                log.error("失败次数过多.明天再试");
                return object;
            }
            if (studentService.bindPhone(token, phone,check_code)){
                if (studentService.bindingSuccess(desUtil.getOpenId(token))){
                    object.put("code",1);
                    object.put("message","绑定成功,获得20逆袭豆");
                    log.error(phone+"绑定成功,获得20逆袭豆");
                    return object;
                }else {
                    object.put("code",1);
                    object.put("message","绑定成功,但获取逆袭豆失败,请联系管理员");
                    log.error(phone+"绑定成功,但获取逆袭豆失败,请联系管理员");
                    return object;
                }
            }else {
                object.put("code",0);
                object.put("message","验证码错误");
                log.error("验证码错误");
                //验证失败则次数加一
                redisUtil.errorPlus(phone+"Error");
                return object;
            }
        }else {
            object.put("code","0");
            object.put("message","token未通过检验");
            log.error("token未通过检验!!");
            return object;
        }
    }
    //绑定邮箱√
    @PutMapping("/student/binding/email")
    public JSONObject studentBindingEmail(String token,String email,int check_code) throws Exception {
        log.error("请求绑定邮箱: "+token+email+check_code);
        JSONObject object = new JSONObject();
        if (studentService.verifyToken(token)){
            if (!redisUtil.stillHavaChance(email+"Error")){
                object.put("code",0);
                object.put("message","失败次数过多.明天再试");
                log.error("失败次数过多.明天再试");
                return object;
            }
            if (studentService.bindEmail(token, email, check_code)){
                if (studentService.bindingSuccess(desUtil.getOpenId(token))){
                    object.put("code",1);
                    object.put("message","绑定成功,获得20逆袭豆");
                    log.error("绑定成功,获得20逆袭豆");
                    return object;
                }else{
                    object.put("code",1);
                    object.put("message","绑定成功,但获取逆袭豆失败,请联系管理员");
                    log.error("绑定成功,但获取逆袭豆失败,请联系管理员");
                    return object;
                }
            }else {
                object.put("code",0);
                object.put("message","绑定失败");
                log.error("绑定邮箱失败");
                redisUtil.errorPlus(email+"Error");
                return object;
            }
        }else {
            object.put("code","0");
            object.put("message","token未通过检验");
            log.error("token未通过检验!!");
            return object;
        }
    }
    //发邮件√
    @PostMapping("/student/email")
    public JSONObject studentSendM(String email) throws IOException {
        log.error("邮箱是: "+email);
        final int random_code =random.nextInt(899999)+100000;
        JSONObject object = mpService.sendMail(email,random_code);
        return object;
    }
    //发短信√
    @PostMapping("/student/message")
    public JSONObject studentSendM(Long phone) throws IOException, ClientException {
        log.error("手机是: "+phone);
        final int random_code =random.nextInt(899999)+100000;
        JSONObject object = smsService.sendSms(phone,random_code);
        //redis有效时间.为便于测试 设为20分钟
        stringRedisTemplate.opsForValue().set(String.valueOf(phone), String.valueOf(random_code),60*5, TimeUnit.SECONDS);
        return object;
    }
    //图片上传√
    @RequestMapping(value = "/student/picture",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject postPicture(MultipartFile file) throws IOException {
        log.error("请求上传图片: "+file.getOriginalFilename());
        JSONObject object = new JSONObject();
        String name = file.getOriginalFilename();
        String end = name.substring(name.length()-5,name.length());
        String objectName = String.valueOf(System.currentTimeMillis())+end.substring(end.indexOf("."),end.length());
        log.error(objectName);
        if (ossService.uploadFile(objectName,file)){
            object.put("code","1");
            object.put("message","图片上传成功");
            object.put("data","https://wangquan-picture-storage.oss-cn-beijing.aliyuncs.com/"+objectName);
        }else {
            object.put("code","0");
            object.put("message","图片上传失败");
            object.put("data",null);
            log.error("调用uploadFile方法出错!!");
        }
        log.error(object);
        return object;
    }
    /*
    * 测试
    * */
    /*//测试参数校验
    @PostMapping("/testVali")
    public JSONObject testV(@Valid Student student, BindingResult result){
        JSONObject object = new JSONObject();
        if (result.hasErrors()) {
            object.put("code",0);
            object.put("message", "参数不合法 :"+result.getAllErrors());
            return object;
        }
        object.put("code",1);
        object.put("message","成功");
        return object;
    }
    //校验
    @PostMapping("/test/verify")
    public Boolean testVerify(String token,String openId) throws Exception {
        studentService.getInfo(token);
        return studentService.verifyToken(token);
    }
    @GetMapping("/test/status")
    public JSONObject testStatus(String openId){
        JSONObject object = new JSONObject();
        if (! studentService.checkStudentStatus(openId)){
            log.error(openId+",该用户已被冻结");
            object.put("code",2);
            object.put("message","该用户已被冻结");
        }else {
            log.error(openId+",该用户正常");
            object.put("code",1);
            object.put("message","登录成功");
        }
        return object;
    }*/
}
