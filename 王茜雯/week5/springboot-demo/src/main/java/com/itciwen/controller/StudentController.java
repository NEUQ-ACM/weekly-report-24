package com.itciwen.controller;

import com.itciwen.dao.Student;
import com.itciwen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//请求处理类
@RestController
public class StudentController {

    //service层代码注入
    @Autowired
    private StudentService studentService;

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable long id){
        return studentService.getStudentById(id);
    }
}
