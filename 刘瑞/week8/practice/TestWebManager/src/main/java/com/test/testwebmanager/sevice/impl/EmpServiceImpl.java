package com.test.testwebmanager.sevice.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.test.testwebmanager.mapper.EmpMapper;
import com.test.testwebmanager.pojo.Emp;
import com.test.testwebmanager.pojo.PageBean;
import com.test.testwebmanager.sevice.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize,
                         String name, Short gender, LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);

        List<Emp> empList = empMapper.list(name, gender, begin, end);
        Page<Emp> p= (Page<Emp>) empList;
//        return new PageBean(empMapper.count(),
//                empMapper.page((page-1)*pageSize, pageSize));
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void add(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Override
    public Emp login(Emp emp) {
        return empMapper.getByUsernameAndPassword(emp);
    }
}
