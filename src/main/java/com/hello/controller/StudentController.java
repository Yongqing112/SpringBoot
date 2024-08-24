package com.hello.controller;

import com.hello.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @RequestMapping("/students/insert")
    public String insert(){
        String sql = "INSERT INTO user(id, user_name) VALUES (3, 'John')";
        Map<String, Object> map = new HashMap<>();
        namedParameterJdbcTemplate.update(sql, map);
        return "執行 INSERT SQL";
    }

    @RequestMapping("/students/insert/map")
    public String insertMap(@RequestBody Student student){
        String sql = "INSERT INTO user(id, user_name) VALUES (:studentId, :studentName)";
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", student.getId());
        map.put("studentName", student.getName());
        namedParameterJdbcTemplate.update(sql, map);
        return "執行 INSERT SQL";
    }

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
