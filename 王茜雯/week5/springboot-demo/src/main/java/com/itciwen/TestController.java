package com.itciwen;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//请求处理类
@RestController
public class TestController {
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World~";
    }
}
