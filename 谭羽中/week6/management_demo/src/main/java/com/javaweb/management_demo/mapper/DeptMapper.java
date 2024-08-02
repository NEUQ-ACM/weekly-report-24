package com.javaweb.management_demo.mapper;

import com.javaweb.management_demo.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select * from monkey.dept")
    List<Dept> list();

    @Delete("delete from monkey.dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into monkey.dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    void insert(Dept dept);
}
