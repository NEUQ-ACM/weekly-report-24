package practice_project.project.service;

import practice_project.project.pojo.Emp;
import practice_project.project.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageBean pagesearch(Integer page, Integer pageSize, String name, Short gender, LocalDate begin,LocalDate end);

    void delete(List<Integer> ids);

    void add(Emp emp);

    Emp getById(Integer id);

    void Update(Emp emp);

    Emp login(Emp emp);
}
