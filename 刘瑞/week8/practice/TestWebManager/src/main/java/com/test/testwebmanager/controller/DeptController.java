package com.test.testwebmanager.controller;

import com.test.testwebmanager.pojo.Dept;
import com.test.testwebmanager.pojo.Result;
import com.test.testwebmanager.sevice.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result list(){
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.list();

        return Result.success(deptList);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门");
        deptService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("添加部门:{}",dept);
        deptService.insert(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        Dept dept = deptService.getDeptById(id);
        log.info("根据id查询部门数据:{}",dept);
        return Result.success(dept);
    }

    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("更新部门");
        deptService.update(dept);
        return Result.success();
    }
}
