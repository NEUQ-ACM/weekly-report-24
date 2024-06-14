package com.xjedu.test;

import com.xjedu.entity.UserEntity;
import com.xjedu.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Mybatis {
    public static void main(String[] args) {

        //创建sqlsession
        SqlSession sqlSession = null;
        try {
            String resource = "Mybatis-config.xml";
            //读取mybatis配置文件
            InputStream inputStream = Resources.getResourceAsStream(resource);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);



            //创建usermap的接口实例
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建userentity的实例
            UserEntity userEntity = new UserEntity();
            //对数据进行插入操作
           userEntity.setUsername("小飞侠");
            userEntity.setPassword("123456");
            //调用插入方法
           int i=userMapper.insert(userEntity);
           //提交
           sqlSession.commit();
            //判断插入语句是否执行成功
            if(i>0){
                System.out.println("数据插入成功");
            }else{
                System.out.println("数据插入失败");
            }

            //对数据进行修改
            userEntity.setUsername("小飞侠");
            userEntity.setPassword("45678");
            //调用修改方法
            int j=userMapper.update(userEntity);
            sqlSession.commit();
            //判断修改语句是否执行成功
            if(j>0){
                System.out.println("数据修改成功");
            }else{
                System.out.println("数据修改失败");
            }


            //对数据进行删除
            userEntity.setUsername("李四");
            //调用删除方法
            int k=userMapper.delete(userEntity);
            sqlSession.commit();
            //判断删除语句是否执行成功
            if(k>0){
                System.out.println("数据删除成功");
            }else{
                System.out.println("数据删除失败");
            }

            //对数据进行查询
            userEntity.setUsername("李四");
            //调用查询方法
            UserEntity result=userMapper.select(userEntity);
            //判断查询语句是否执行成功
            if(result!=null){
                System.out.println("数据查询成功");
                System.out.println(userEntity.getUsername()+"的密码为"+result.getUsername());
            }else{
                System.out.println("数据查询失败");
            }




        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally{
            sqlSession.close();
        }

    }

}
