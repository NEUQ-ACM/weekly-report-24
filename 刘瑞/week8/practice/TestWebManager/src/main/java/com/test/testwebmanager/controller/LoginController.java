package com.test.testwebmanager.controller;

import com.test.testwebmanager.pojo.Emp;
import com.test.testwebmanager.pojo.Result;
import com.test.testwebmanager.sevice.EmpService;
import com.test.testwebmanager.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private EmpService empService;
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("员工登录：{}",emp);
        Emp e=empService.login(emp);

        if(e!=null){
            Map<String,Object> map=new HashMap<>();
            map.put("id",e.getId());
            map.put("name",e.getName());
            map.put("username",e.getUsername());
            String jwt = JwtUtils.generateJwt(map);
            log.info("jwt:{}",jwt);
            return Result.success(jwt);
        }
        else{
            return Result.error("用户或密码错误");
        }
    }
}
