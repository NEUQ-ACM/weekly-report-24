package com.itciwen.mapper;

import com.itciwen.pojo.Emp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface EmpMapper {

    /**
     * 查询总记录数
     * @return
     *//*
    @Select("SELECT COUNT(*) FROM emp")
    public Long count();

    *//**
     * 分页查询，获取列表数据
     * @param start
     * @param pageSize
     * @return
     *//*
    @Select("SELECT * FROM emp LIMIT #{start},#{pageSize}")
    public List<Emp> page(Integer start, Integer pageSize);*/

    /**
     * 员工信息的查询
     * @return
     */
    //@Select("SELECT * FROM emp")
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增员工
     * @param emp
     */
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
            " values(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);
}
