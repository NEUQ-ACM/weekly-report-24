package practice_project.project.service;

import java.util.List;
import practice_project.project.pojo.Dept;

public interface DeptService {

    //查询全部部门数据
    List<Dept> list();

    void delete(Integer id);

    void add(Dept dept);

    Dept search(Integer id);

    void update(Dept dept);

}
