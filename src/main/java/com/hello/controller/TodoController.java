package com.hello.controller;

import com.hello.entity.Todo;
import com.hello.service.impl.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public Iterable<Todo> getTodoList(){

        return todoService.getTodos();
    }
}
