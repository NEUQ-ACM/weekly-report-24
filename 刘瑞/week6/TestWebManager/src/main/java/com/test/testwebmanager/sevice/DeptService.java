package com.test.testwebmanager.sevice;

import com.test.testwebmanager.pojo.Dept;

import java.util.List;

public interface DeptService {
    Dept getDeptById(Integer id);

    List<Dept> list();

    void delete(Integer id);

    void insert(Dept dept);

    void update(Dept dept);
}
