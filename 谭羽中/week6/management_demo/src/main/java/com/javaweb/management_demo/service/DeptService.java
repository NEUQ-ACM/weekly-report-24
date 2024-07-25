package com.javaweb.management_demo.service;

import com.javaweb.management_demo.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();

    void delete(Integer id);

    void add(Dept dept);
}
