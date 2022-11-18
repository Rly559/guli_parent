package com.atguigu.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/10 16:31
 */
@SpringBootApplication
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.cms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
