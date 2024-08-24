package com.hello.controller;

import com.hello.entity.Student;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @PostMapping("/students")
    public String create(@RequestBody Student student){
        return "執行資料庫的Create操作";
    }

    @GetMapping("/students/{id}")
    public String read(@PathVariable String id){
        return "執行資料庫的Read操作";
    }

    @PutMapping("/students/{id}")
    public String update(@PathVariable String id,
                         @RequestBody Student student){
        return "執行資料庫的Update操作";
    }

    @DeleteMapping("/students/{id}")
    public String delete(@PathVariable String id){
        return "執行資料庫的Delete操作";
    }
}
