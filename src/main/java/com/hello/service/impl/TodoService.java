package com.hello.service.impl;

import com.hello.dao.TodoDao;
import com.hello.entity.TodoList.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class TodoService {

    @Autowired
    TodoDao todoDao;

    public Iterable<Todo> getTodo(){
        return todoDao.findAll();
    }

    public Iterable<Todo> createTodo(Todo todo){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));

        String date = df.format(new Date());
        todo.setCreateTime(date);
        todo.setUpdateTime(date);
        todoDao.save(todo);
        return getTodo();
    }
}
