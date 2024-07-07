package com.example.demo.service.implement;

import com.example.demo.dao.EmpDao;
import com.example.demo.pojo.Emp;
import com.example.demo.service.EmpService;
import com.example.demo.service.transformInformation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
public class EmpServiceA implements EmpService {
    @Autowired
    private EmpDao empDao;
    @Override
    public List<Emp> listEmp() {
        List<Emp> empList = empDao.listEmp();
        empList.stream().forEach(emp->{
//            String gender = emp.getGender();
//            if("1".equals(gender)){
//                emp.setGender("男");
//            }
//            else if("2".equals(gender)) {
//                emp.setGender("女");
//            }
            transformInformation gender= self->self.equals("1") ? "男" : self.equals("2") ? "女" : "错误";
            emp.setGender(gender.transforminformation(emp.getGender()));

            transformInformation job=self->self.equals("1") ? "讲师" : self.equals("2") ? "班主任" : self.equals("3")?"就业指导":"错误";
            emp.setJob(job.transforminformation(emp.getJob()));
        });
        return empList;
    }
}
