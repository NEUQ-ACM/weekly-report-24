package com.javaweb.service;

import com.javaweb.entity.User;
import com.javaweb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public List<User> findAll(){
        return userMapper.findAll();
    }
}
