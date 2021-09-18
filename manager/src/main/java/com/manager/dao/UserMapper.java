package com.manager.dao;

import com.manager.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {

    @Select("select * from t_user where user_id =#{userId}")
    @Results({
            @Result(property="userId", column="user_id"),
            @Result(property="userName", column="user_name"),
            @Result(property="gender", column="gender"),
            @Result(property="phone", column="phone"),
            @Result(property="email", column="email"),
            @Result(property="createTime", column="create_time"),
            @Result(property="updateTime", column="update_time")
    })
    User findUserById(@Param("userId") Long userId);
}
