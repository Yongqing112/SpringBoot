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

    public Iterable<Todo> createTodo(Todo todo){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));

        String date = df.format(new Date());
        todo.setCreateTime(date);
        todo.setUpdateTime(date);
        todoDao.save(todo);
        return getTodo();
    }

    public Iterable<Todo> getTodo(){
        return todoDao.findAll();
    }

    public Todo findById(Integer id){
        return todoDao.findById(id).get();
    }

    public Todo updateTodo(Integer id, Todo todo){
        try {
            Todo resTodo = findById(id);
            Integer status = todo.getStatus();
            resTodo.setStatus(status);
            return todoDao.save(resTodo);
        }
        catch (Exception exception){
            return null;
        }
    }

    public Boolean deleteTodo(Integer id){
        try {
            Todo resTodo = findById(id);
            todoDao.deleteById(id);
            return true;
        }
        catch (Exception exception){
            return null;
        }
    }
}
