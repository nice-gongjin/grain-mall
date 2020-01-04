package com.gj.gmall.config;

import com.gj.gmall.filter.AuthorityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    AuthorityFilter authorityFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authorityFilter).addPathPatterns("/**").excludePathPatterns("/error");
        super.addInterceptors(registry);
    }

}
