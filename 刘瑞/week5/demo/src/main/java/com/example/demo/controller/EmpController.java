package com.example.demo.controller;

import com.example.demo.pojo.Emp;
import com.example.demo.pojo.Result;
import com.example.demo.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpController {
    @Autowired
    private EmpService empService;
    @RequestMapping("/listEmp")
    public Result list(){
        List<Emp> empList = empService.listEmp();
        return Result.success(empList);
    }
}
//String file = this.getClass().getClassLoader().getResource("emp.xml").getFile();
//        System.out.println(file);
//List<Emp> empList=XmlParserUtils.parse(file, Emp.class);
//        empList.stream().forEach(emp->{
////            String gender = emp.getGender();
////            if("1".equals(gender)){
////                emp.setGender("男");
////            }
////            else if("2".equals(gender)) {
////                emp.setGender("女");
////            }
//transformInformation gender=self->self.equals("1") ? "男" : self.equals("2") ? "女" : "错误";
//            emp.setGender(gender.transforminformation(emp.getGender()));
//
//transformInformation job=self->self.equals("1") ? "讲师" : self.equals("2") ? "班主任" : self.equals("3")?"就业指导":"错误";
//            emp.setJob(job.transforminformation(emp.getJob()));
//        });
//        System.out.println(empList);