package com.hello.service;

import com.hello.entity.TodoList.UUser;

import java.util.Optional;

public interface UserService {

    public String queryUserName(String id);

    public Optional<UUser> getTodosByUserId(Integer id);
}
