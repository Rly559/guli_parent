package com.atguigu.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/18 15:49
 */
@SpringBootApplication
@ComponentScan({"com.atguigu"})
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
