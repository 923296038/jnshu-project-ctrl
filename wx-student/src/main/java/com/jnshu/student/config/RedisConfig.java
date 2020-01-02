package com.jnshu.student.config;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching //配置cache Manager
//继承CachingConfigurerSupport类是为了自定义redis key的生成策略（复写keyGenerator方法）
public class RedisConfig extends CachingConfigurerSupport {
    //从配置文件中读取配置信息

    private Long redisTimeout;



    /**
     * RedisTemplate配置
     * jdk序列方式，用来保存对象(key=对象)
     * @param factory
     * @return
     */
	  /*@Bean
	  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) { 
	   
	   StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	   //自定义的redis序列化工具类（对object序列化）
       RedisObjectSerializer redisObjectSerializer = new RedisObjectSerializer();
       RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
       template.setConnectionFactory(factory);
       template.setKeySerializer(stringRedisSerializer);
       template.setValueSerializer(redisObjectSerializer);
       
	   return template; 
	  } */
    /**
     * RedisTemplate配置
     * string的序列化方式，存储string格式（key=value）
     * @param factory
     * @return
     */
	  /*@Bean
	  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) { 
	   StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(); 
	   stringRedisTemplate.setConnectionFactory(factory);
	   setSerializer(stringRedisTemplate);
	   stringRedisTemplate.afterPropertiesSet();
	   return stringRedisTemplate; 
	  }*/
	  
	  /*private void setSerializer(StringRedisTemplate template){
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new                 
            Jackson2JsonRedisSerializer(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        template.setValueSerializer(jackson2JsonRedisSerializer);
    }*/

    /**
     * redis模板，存储关键字是字符串，值是Jdk序列化,这个写法和我com.jary.util中的RedisObjectSerializer写法更简单，测试后选择使用那个
     * @Description:
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //key序列化方式;但是如果方法上有Long等非String类型的话，会报类型转换错误；
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        //JdkSerializationRedisSerializer序列化方式;
        JdkSerializationRedisSerializer jdkRedisSerializer=new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}