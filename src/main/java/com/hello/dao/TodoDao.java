package com.hello.dao;

import com.hello.entity.TodoList.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoDao extends CrudRepository<Todo, Integer> {
}
