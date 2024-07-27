package com.test.testwebmanager.exception;

import com.test.testwebmanager.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        e.printStackTrace();
        return Result.error("错误");
    }
}
