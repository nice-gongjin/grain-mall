package com.grain.mall.GlobalException;

import com.gj.util.R;
import com.grain.mall.util.myException.MyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceHandler {

    @ExceptionHandler(MyException.class)
    public R myException(Exception e) {
        return R.error("user的全局异常处理自定义的MyException异常：" + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e){
        return R.error("user的全局异常处理的Exception错误：" + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(RuntimeException r){
        return R.error("user的全局异常处理的RuntimeException错误：" + r.getMessage());
    }

}
