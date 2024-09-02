package com.hello.controller.TodoList;

import com.hello.entity.TodoList.Todo;
import com.hello.service.impl.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity createTodo(@RequestBody Todo todo){
        Integer rlt = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(rlt);
    }

    @GetMapping("/todos")
    public ResponseEntity getTodos(){
        Iterable<Todo> todoList = todoService.getTodos();

        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

    @GetMapping("/todos/{id}")
    public Optional<Todo> getTodo(@PathVariable Integer id){
        Optional<Todo> todo = todoService.findById(id);
        return todo;
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        Boolean rlt = todoService.updateTodo(id, todo);
        if(rlt){
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status 欄位不可為空");
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity deleteTodo(@PathVariable Integer id){
        Boolean rlt = todoService.deleteTodo(id);
        if(rlt){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 不存在");
        }
    }
}
