package com.grain.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//public class MyWebMvcConfig implement WebMvcConfigurer { 2.x以上用这个类
public class MyWebMvcConfig extends WebMvcConfigurerAdapter {

    // 设置CORS的跨域请求问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    // /** 表示本应用的所有方法都会去处理跨域请求， allowedMethods 表示允许通过的请求数，maxAge (1800)有效期默认1800秒
    // allowedHeaders 则表示允许的请求头。    经过这样的配置之后，就不必在每个方法上单独配置跨域了。
    registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .maxAge(1800)
            .allowCredentials(true)
            .allowedHeaders("*");
    }

}
