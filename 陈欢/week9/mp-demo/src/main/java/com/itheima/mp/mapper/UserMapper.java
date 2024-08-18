package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    void updateBalanceByIds(@Param("ew") LambdaUpdateWrapper<User> wrapper,@Param("amount") int amount);

    @Update("update user set balance = balance - #{money} where id = #{id} ")
    void deductBalance(@Param("id") Long id,@Param("money") Integer money);
}
