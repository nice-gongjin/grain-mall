package com.gj.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.gj.gmall.mapper")
public class GmallPayCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPayCenterApplication.class, args);
    }

}
