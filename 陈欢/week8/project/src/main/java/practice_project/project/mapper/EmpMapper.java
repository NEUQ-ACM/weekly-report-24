package practice_project.project.mapper;

import org.apache.ibatis.annotations.*;
import practice_project.project.pojo.Emp;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

//    @Select("select count(*) from emp")
//    public Long count();
//
//    @Select("select * from emp limit #{start},#{pageSize}")
//    public List<Emp> page(Integer start,Integer pageSize);
//    @Select("select * from emp")
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    public void delete(List<Integer> ids);

    @Insert("insert into emp (username,name,gender,image,job,entrydate,dept_id,create_time,update_time) " +
            "values (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime});")
    void insert(Emp emp);

    @Select("select * from emp where id = #{id}")
    Emp getById(Integer id);

//    @Update("update emp set username = #{username}, name = #{name},gender = #{gender}," +
//            "image = #{image},dept_id = #{deptId},job = #{job},entrydate = #{entrydate} where id = #{id}")
    void Update(Emp emp);

    //根据用户名和密码查询员工
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp getByUsernameAndPassword(Emp emp);
}
