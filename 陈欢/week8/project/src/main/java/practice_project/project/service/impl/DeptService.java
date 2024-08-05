package practice_project.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import practice_project.project.pojo.Dept;
import org.springframework.stereotype.Service;
import practice_project.project.mapper.DeptMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptService implements practice_project.project.service.DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    public void delete(Integer id)
    {
        deptMapper.deleteById(id);
    }

    public void add(Dept dept)
    {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

    public Dept search(Integer id)
    {
        return (Dept) deptMapper.searchById(id);
    }

    public void update(Dept dept)
    {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateById(dept);
    }
}
