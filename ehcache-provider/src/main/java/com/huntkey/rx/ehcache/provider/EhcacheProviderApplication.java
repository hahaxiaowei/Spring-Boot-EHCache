package com.huntkey.rx.ehcache.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by sunwei on 2017/12/20 Time:17:52
 */
/**
 * 链接Mysql数据库简单的集成Mybatis、ehcache框架采用MapperXml访问数据库。
 *
 * 简单用户链接Mysql数据库微服务（通过 mybatis 链接 mysql 并用 MapperXml 编写数据访问，并且通过 EhCache 缓存来访问）。
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableCaching
public class EhcacheProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EhcacheProviderApplication.class,args);
        System.out.println("【【【【【【 链接MysqlMybatisMapperEhCache数据库微服务 】】】】】】已启动.");
    }
}
