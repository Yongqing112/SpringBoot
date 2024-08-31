package com.hello.controller.Student;

import com.hello.dto.StudentRequest;
import com.hello.entity.other.Student;
import com.hello.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private StudentService<Student> studentService;

    @RequestMapping("/students/insert")
    public String insert(){
        String sql = "INSERT INTO user(id, user_name) VALUES (3, 'John')";
        Map<String, Object> map = new HashMap<>();
        namedParameterJdbcTemplate.update(sql, map);
        return "執行 INSERT SQL";
    }

    @RequestMapping("/students/insert/map")
    public Student insertMap(@RequestBody Student student){
        String sql = "INSERT INTO user(id, user_name) VALUES (:studentId, :studentName)";
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", student.getId());
        map.put("studentName", student.getName());
        namedParameterJdbcTemplate.update(sql, map);
        return studentService.readById(student.getId());
    }

    @RequestMapping("/students/query/{id}")
    public Student query(@PathVariable Integer id){

        return studentService.readById(id);
    }

    @PostMapping("/students")
    public Student create(@RequestBody StudentRequest studentRequest){
        // insert
        Integer studentId = studentService.createStudent(studentRequest);

        Student student = studentService.readById(studentId);
        return student;
    }

    @GetMapping("/students/{id}")
    public Student read(@PathVariable Integer id){
        Student student = studentService.readById(id);
        return student;
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> update(@PathVariable Integer id,
                         @RequestBody StudentRequest studentRequest){
        // 檢查 student 是否存在
        Student student = studentService.readById(id);

        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改 Student 的數據
        studentService.update(id, studentRequest);

        Student updateStudent = studentService.readById(id);

        return ResponseEntity.status(HttpStatus.OK).body(updateStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
