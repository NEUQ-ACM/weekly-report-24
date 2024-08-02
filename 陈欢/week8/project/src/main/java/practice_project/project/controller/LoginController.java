package practice_project.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice_project.project.pojo.Emp;
import practice_project.project.pojo.Result;
import practice_project.project.service.EmpService;
import practice_project.project.utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp)
    {
        log.info("员工登录:{}", emp);
        Emp e = empService.login(emp);
        //登录成功，生成并下发令牌
        if(e!=null)
        {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());

            String jwt = JwtUtils.generateJwt(claims);//jwt包含当前登录的员工信息
            return Result.success(jwt);
        }

        //登录失败，返回错误信息
        return Result.error("用户名或密码错误");
    }
}
