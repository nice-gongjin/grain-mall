package com.gj.gmall.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidatorAop {

    @Around(value = "execution(public * com.gj.gmall.*Controller.*.*(..))")
    public Object around(JoinPoint joinPoint){
        try {
            log.info("****** aop开始处理。。。");
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0){

            }
            log.info("****** aop处理完成。。。");
        }catch (Throwable throwable) {
            log.error("*** 切面拦截异常！ {}",throwable.getMessage());
            throw new RuntimeException(throwable);
        }finally {
            log.info("****** aop后置通知。。。");
        }
        return null;
    }

}
