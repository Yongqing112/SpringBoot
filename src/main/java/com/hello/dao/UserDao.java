package com.hello.dao;

import com.hello.entity.TodoList.Todo_User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<Todo_User, Integer> {
}
