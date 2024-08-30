package com.hello.service.impl;

import com.hello.dao.TodoDao;
import com.hello.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    TodoDao todoDao;

    public Iterable<Todo> getTodos(){
        return todoDao.findAll();
    }
}
