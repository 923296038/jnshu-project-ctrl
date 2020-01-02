package com.jnshu.student.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-31 17:22
 **/
@Component
public class RedisUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public Long errorPlus(String key){
        Long error = 0L;
        System.out.println("正在请求errorPlus-----------");
        if (!stringRedisTemplate.hasKey(key)){
            stringRedisTemplate.opsForValue().set(key,error.toString());
            System.out.println("该key对应value为null,已为其设为0");
        }
        error = Long.valueOf(stringRedisTemplate.opsForValue().get(key))+1;
        System.out.println("今日验证失败次数:"+error);
        int seconds = getRemainSecondsToTomorrow();
        stringRedisTemplate.opsForValue().set(key,error.toString(),seconds,TimeUnit.SECONDS);
        System.out.println("设置的过期失效是:"+seconds);
        return error;
    }
    public boolean stillHavaChance(String key){
        Long error = 0L;
        System.out.println("正在请求stillHavaChance---------");
        if (stringRedisTemplate==null)
            System.out.println("stringRedisTemp为null");
        if (!stringRedisTemplate.hasKey(key)){
            stringRedisTemplate.opsForValue().set(key,error.toString());
            System.out.println("该key对应value为null,已为其设为0");
        }
        System.out.println(Long.valueOf(stringRedisTemplate.opsForValue().get(key)));
        return Long.valueOf(stringRedisTemplate.opsForValue().get(key))<4;
    }

    public int getRemainSecondsToTomorrow(){
        Calendar calendar = Calendar.getInstance();
        String midnight = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 23:59:59";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 转换为今天
            Date latDate=sdf.parse(midnight);
            // 得到的毫秒 除以1000转换 为秒
            return (int)(latDate.getTime()-System.currentTimeMillis())/1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        RedisUtil redisUtil =new RedisUtil();
        redisUtil.errorPlus(13972102240L+"Error");
        System.out.println(redisUtil.stillHavaChance("13972102240Error"));
    }
}
