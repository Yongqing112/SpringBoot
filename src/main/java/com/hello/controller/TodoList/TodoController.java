package com.hello.controller.TodoList;

import com.hello.entity.TodoList.Todo;
import com.hello.service.impl.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/todos")
    public String createTodo(@ModelAttribute Todo todo, Model model){
        Iterable<Todo> allTodoList = todoService.createTodo(todo);
        Todo emptyTodo = new Todo();
        model.addAttribute("todoList", allTodoList);
        model.addAttribute("todoObject", emptyTodo);
        return "redirect:/todos";
    }

    @GetMapping("/todos")
    public String getTodoList(Model model){
        Iterable<Todo> todoList = todoService.getTodo();
        model.addAttribute("todoList", todoList);
        Todo todo = new Todo();
        model.addAttribute("todoObject", todo);
        return  "todoList";
    }

    @PutMapping("/todos/{id}")
    public String updateTodo(@PathVariable Integer id, @RequestBody Todo todo){
        todoService.updateTodo(id, todo);
        return "redirect:/todos";
    }

    @DeleteMapping("/todos/{id}")
    public String deleteTodo(@PathVariable Integer id){
        todoService.deleteTodo(id);
        return "redirect:/todos";
    }
}
