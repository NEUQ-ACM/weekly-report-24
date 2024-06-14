package com.xjedu.mapper;

import com.xjedu.entity.UserEntity;

public interface UserMapper {
    //生命数据添加
    int insert(UserEntity userEntity);
    //声明数据更新
    int update(UserEntity userEntity);
    //声明数据添加
    int delete(UserEntity userEntity);
    //声明数据查询
    UserEntity select(UserEntity userEntity);

}
