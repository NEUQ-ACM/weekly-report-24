package com.test.testwebmanager.sevice;

import com.test.testwebmanager.pojo.Emp;
import com.test.testwebmanager.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageBean page(Integer page, Integer pageSize,
                  String name, Short gender, LocalDate begin, LocalDate end);

    void delete(List<Integer> ids);

    void add(Emp emp);

    Emp getById(Integer id);

    void update(Emp emp);

    Emp login(Emp emp);

}
