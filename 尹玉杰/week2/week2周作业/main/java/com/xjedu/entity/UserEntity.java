package com.xjedu.entity;
import lombok.Data;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


@Data
public class UserEntity {
    //设置私有属性id
    private int id;
    private String Username;
    private String password;

}
