package com.example.demo.dao.implement;

import com.example.demo.dao.EmpDao;
import com.example.demo.pojo.Emp;
import com.example.demo.utils.XmlParserUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmpDaoA implements EmpDao {

    @Override
    public List<Emp> listEmp() {
        String file = this.getClass().getClassLoader().getResource("emp.xml").getFile();
        return XmlParserUtils.parse(file, Emp.class);
    }
}
