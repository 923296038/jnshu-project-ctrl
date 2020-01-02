package com.jnshu.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.jnshu.article.dao")
@EnableCaching
public class ArticleApplication {

    public static void main(String[] args) {

        SpringApplication.run(ArticleApplication.class, args);
    }

}
