package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void deductBalance(Long id, Integer money) {
        //查询用户
        User user = getById(id);
        //校验用户状态
        if (user == null || user.getStatus()==2) {
            throw new RuntimeException("用户状态异常");
        }
        //校验余额是否充足
        if(user.getBalance() < money){
            throw new RuntimeException("用户余额不足");
        }
        //扣减金额
        lambdaUpdate()
                .set(User::getBalance, user.getBalance() - money)
                .set(user.getBalance()-money == 0,User::getStatus, 2)
                .eq(User::getId, id)
                .eq(User::getBalance,user.getBalance())
                .update();
    }

    @Override
    public List<User> queryService(String name, Integer status, Integer minBalance, Integer maxBalance) {
        return lambdaQuery()
                .like(name!=null,User::getUsername,name)
                .eq(status!=null,User::getStatus,status)
                .ge(minBalance!=null,User::getBalance,minBalance)
                .le(maxBalance!=null,User::getBalance,maxBalance)
                .list();
    }
}
