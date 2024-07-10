package com.example.demo.controller;

import com.example.demo.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RequestController {
    @RequestMapping("/simpleParam")
    public String simpleParem(HttpServletRequest request){
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        int ageInt = Integer.parseInt(age);
        System.out.println(name+":"+age);
        return "OK";
    }

    @RequestMapping("/simplePojo")
    public String simplePojo(User user){
        System.out.println("user:"+user);
        return "OK";
    }

    @RequestMapping("/listPara")
    public String listPara(@RequestParam List<String> hobby){
        System.out.println(hobby);
        return "OK";
    }

    @RequestMapping("/datePara")
    public String dataPara(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updateTime){
        System.out.println(updateTime);
        return "OK";
    }

    @RequestMapping("/jsonPara")
    public String jsonPara(@RequestBody User user){
        System.out.println(user);
        return "OK";
    }

    @RequestMapping("path/{id}")
    public String pathParam(@PathVariable Integer id){
        System.out.println(id);
        return "OK";
    }
    @RequestMapping("path/{id}/{name}")
    public String pathParam2(@PathVariable Integer id,@PathVariable String name){
        System.out.println(id+":"+name);
        return "OK";
    }
}
