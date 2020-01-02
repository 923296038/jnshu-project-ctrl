package com.jnshu.student.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: jaryle
 * @Date: 2018/08/08 17:30
 */
@Service
public class RedisService {
    //这里我们注入了我自定义的Redistemplate,而且支持<String,Object>存储，上面写出自定义序列化
    @Autowired
    protected RedisTemplate redisTemplate;
    private final static Logger log = LoggerFactory.getLogger(RedisService.class);
    private Long error = 0L;
    /**
     * 写入redis缓存（不设置expire存活时间）
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value){
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }
    //验证出错. 失败次数+1
    public Long errorPlus(String key){
        System.out.println("正在请求errorPlus-----------");
        try {
            boolean result = redisTemplate.hasKey(key);
            ValueOperations operations = redisTemplate.opsForValue();
            if (!result){
                operations.set(key,error.toString());
                System.out.println("该key对应value为null,已为其设为0");
            }
            error = (Long) operations.get(key);
            System.out.println("今日验证失败次数:"+error);
            int seconds = getRemainSecondsToTomorrow();
            operations.set(key,error.toString(),seconds,TimeUnit.SECONDS);
            System.out.println("设置的过期失效是:"+seconds);
        } catch (Exception e) {
            log.error("errorPlus方法失败！错误信息为：" + e.getMessage());
        }
        return error;
    }
    //是否还有尝试机会
    public boolean stillHavaChance(String key){
        System.out.println("正在请求stillHavaChance---------");
        try {
            boolean result = redisTemplate.hasKey(key);
            ValueOperations operations = redisTemplate.opsForValue();
            if (!result){
                operations.set(key,error.toString());
                System.out.println("该key对应value为null,已为其设为0");
            }
            error = (Long) operations.get(key);
            System.out.println("------错误次数"+error);
        } catch (Exception e) {
            log.error("stillHavaChancec方法失败！错误信息为：" + e.getMessage());
        }
        return error<4;
    }
    /**
     * 写入redis缓存（设置expire存活时间）
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean set(final String key, String value, Long expire){
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return result;
    }


    /**
     * 读取redis缓存
     * @param key
     * @return
     */
    public Object get(final String key){
        Object result = null;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            result = operations.get(key);
        } catch (Exception e) {
            log.error("读取redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 判断redis缓存中是否有对应的key
     * @param key
     * @return
     */
    public boolean exists(final String key){
        boolean result = false;
        try {
            result = redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     * @param key
     * @return
     */
    public boolean remove(final String key){
        boolean result = false;
        try {
            if(exists(key)){
                redisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            log.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据keys批量删除对应的value
     * @param keys
     * @return
     */
    public void remove(final String... keys){
        for(String key : keys){
            remove(key);
        }
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
}