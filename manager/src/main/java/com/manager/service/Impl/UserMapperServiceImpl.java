package com.manager.service.Impl;

import com.manager.annotation.DataSource;
import com.manager.dao.UserMapper;
import com.manager.entity.User;
import com.manager.service.UserMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapperServiceImpl implements UserMapperService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUser(Long id) {
        User userById = userMapper.findUserById(id);
        return userById;
    }

    @DataSource("slaveDataSource")
    @Override
    public User findUser1(Long id) {
        User userById = userMapper.findUserById(id);
        return userById;
    }
}
