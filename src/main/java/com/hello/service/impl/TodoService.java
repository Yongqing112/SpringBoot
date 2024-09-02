package com.hello.service.impl;

import com.hello.dao.TodoDao;
import com.hello.entity.TodoList.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    TodoDao todoDao;

    public Integer createTodo(Todo todo){
        Todo rltTodo = todoDao.save(todo);
        return rltTodo.getId();
    }

    public Iterable<Todo> getTodos(){
        return todoDao.findAll();
    }

    public Optional<Todo> findById(Integer id){
        Optional<Todo> todo = todoDao.findById(id);
        return todo;
    }

    public Boolean updateTodo(Integer id, Todo todo){
        Optional<Todo> isExistTodo = findById(id);
        if(!isExistTodo.isPresent()){
            return false;
        }

        Todo newTodo = isExistTodo.get();
        if(newTodo.getStatus() == null){
            return null;
        }
        newTodo.setStatus(todo.getStatus());
        try{
            todoDao.save(newTodo);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Boolean deleteTodo(Integer id){
        Optional<Todo> findTodo = findById(id);
        if(!findTodo.isPresent()){
            return false;
        }
        try{
            todoDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
