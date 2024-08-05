package practice_project.project.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import practice_project.project.pojo.Emp;
import practice_project.project.pojo.PageBean;
import practice_project.project.pojo.Result;
import practice_project.project.service.EmpService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/emps")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end)
    {
        log.info("分页查询，参数：{},{},{},{},{},{}",page,pageSize,name,gender,begin,end);
        PageBean pageBean= empService.pagesearch(page,pageSize,name,gender,begin,end);
        return Result.success(pageBean);
    }

    @DeleteMapping("/emps/{ids}")
    public Result delete(@PathVariable("ids") List<Integer> ids)
    {
        log.info("删除数据参数：{}",ids);
        empService.delete(ids);

        return Result.success();
    }

    @PostMapping("/emps")
    public Result add(@RequestBody Emp emp)
    {
        log.info("添加数据参数：{}",emp);
        empService.add(emp);
        return Result.success();
    }

    @GetMapping("/emps/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询员工信息：{}",id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    @PutMapping("/emps")
    public Result Update(@RequestBody Emp emp)
    {
        log.info("更新员工：{}",emp);
        empService.Update(emp);
        return Result.success();
    }
}
