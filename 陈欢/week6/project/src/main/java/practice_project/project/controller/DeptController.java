package practice_project.project.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice_project.project.pojo.Dept;
import practice_project.project.pojo.Result;
import practice_project.project.service.DeptService;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.core.annotation.MergedAnnotations.search;

@Slf4j
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

//    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping("/depts")
    public <Dept> Result list()
    {
        log.info("查询全部部门数据");

        //调用service查询数据
        List<Dept> deptList = (List<Dept>) deptService.list();

        return Result.success(deptList);
    }

    @DeleteMapping("/depts/{id}")
    public Result delete(@PathVariable Integer id)
    {
        log.info("根据id删除部门：{}",id);
        deptService.delete(id);
        return Result.success();
    }

    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept)
    {
        log.info("新增部门：{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/depts/{id}")
    public Result search(@PathVariable Integer id)
    {
        log.info("查询id为{}的部门",id);
        Dept dept = deptService.search(id);
        return Result.success(dept);
    }

    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept)
    {
        log.info("更新部门：{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}
