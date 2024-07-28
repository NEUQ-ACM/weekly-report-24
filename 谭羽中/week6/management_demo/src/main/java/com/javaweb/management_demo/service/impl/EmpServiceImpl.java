package com.javaweb.management_demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.javaweb.management_demo.mapper.EmpMapper;
import com.javaweb.management_demo.pojo.Emp;
import com.javaweb.management_demo.pojo.PageBean;
import com.javaweb.management_demo.service.EmpService;
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
    public PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);

        List<Emp> empList = empMapper.list(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;

        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }
}






















