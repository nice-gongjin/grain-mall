package com.gj.gmall.aop;

import com.gj.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceHandler {

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e){
        return R.error("222 Exception错误：" + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(RuntimeException r){
        return R.error("222 RuntimeException错误：" + r.getMessage());
    }

}
