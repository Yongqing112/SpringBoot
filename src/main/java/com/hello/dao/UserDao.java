package com.hello.dao;

import com.hello.entity.TodoList.UUser;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UUser, Integer> {
}
