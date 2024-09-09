package com.hello.service.impl;

import com.hello.dao.UserDao;
import com.hello.entity.TodoList.UUser;
import com.hello.mapper.UserMapper;
import com.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Override
    public String queryUserName(String id) {
        return userMapper.queryUserName(id);
    }

    @Override
    public Optional<UUser> getTodosByUserId(Integer id) {
        return userDao.findById(id);
    }
}
