package com.jnshu.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-11 15:26
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class ManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class,args);
    }
}
