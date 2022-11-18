package com.atguigu.servicebase.exceptionhandler;
import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/9/21 15:50
 *
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //指定出现什么异常时执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理..");
    }

    //特定异常处理
}
