package com.gj.gmall.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ValidatorAop {

    @Around("execution(* com.gj.gmall.controller.*Controller..*(..))")
    public Object around(ProceedingJoinPoint joinPoint){
        log.info("****** aop开始 666 ");
        Object proceed = null;
        try {
            log.info("****** aop开始处理。。。");
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0){
                for (Object arg : args) {
                    log.info("****** arg = " + arg);
                }
            }
            proceed = joinPoint.proceed(args);
            log.info("****** aop处理完成。。。" + proceed);
        }catch (Throwable throwable) {
            log.error("*** 切面拦截的异常错误！ {}",throwable.getMessage());
            throw new RuntimeException(throwable);
        }finally {
            log.info("****** aop后置通知。。。");
        }

        return proceed;
    }

}
