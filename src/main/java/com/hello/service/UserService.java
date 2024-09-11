package com.hello.service;

import com.hello.entity.TodoList.Todo_User;

import java.util.Optional;

public interface UserService {

    public String queryUserName(String id);

    public Optional<Todo_User> getTodosByUserId(Integer id);
}
