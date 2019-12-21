package com.gj.manage;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.gj.manage.mapper")
public class GmallManageUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallManageUserApplication.class, args);
    }

}
