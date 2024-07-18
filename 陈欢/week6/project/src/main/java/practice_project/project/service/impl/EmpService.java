package practice_project.project.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice_project.project.mapper.EmpMapper;
import practice_project.project.pojo.Emp;
import practice_project.project.pojo.PageBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpService implements practice_project.project.service.EmpService {

    @Autowired
    private EmpMapper empMapper;

//    public PageBean pagesearch(Integer page,Integer pageSize)
//    {
//        //获取总记录数
//        Long count = empMapper.count();
//        //获取分页查询结果列表
//        Integer start = (page-1)*pageSize;
//        List<Emp> list = empMapper.page(start,pageSize);
//        //封装对象
//        PageBean pageBean = new PageBean(count,list);
//
//        return pageBean;
//    }
    public PageBean pagesearch(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end)
    {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询
        List<Emp> emplist = empMapper.list(name,gender,begin,end);
        Page<Emp> p = (Page<Emp>) emplist;
        //封装对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    public void delete(List<Integer> ids)
    {
        empMapper.delete(ids);
    }

    public void add(Emp emp)
    {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }
}
