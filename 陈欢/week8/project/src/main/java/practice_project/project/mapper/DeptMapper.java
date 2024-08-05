package practice_project.project.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import practice_project.project.pojo.Dept;

import java.util.List;

@Mapper
public interface DeptMapper {
    //查询全部部门数据
    @Select("select * from dept")
    List<Dept> list();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    @Select("select id,name from dept where id = #{id}")
    Dept searchById(Integer id);

    @Update("update dept set name = #{name},update_time = #{updateTime}" +
            " where id = #{id}")
    void updateById(Dept dept);
}
