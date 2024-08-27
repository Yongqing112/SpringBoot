package com.hello.dao.impl;

import com.hello.dao.Dao;
import com.hello.dto.Request;
import com.hello.dto.StudentRequest;
import com.hello.entity.Student;
import com.hello.mapper.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDao implements Dao<Student> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer create(Request request) {
        String sql = "INSERT INTO student(id, name) VALUES (:studentId, :studentName)";
        StudentRequest studentRequest = (StudentRequest) request;
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", studentRequest.getId());
        map.put("studentName", studentRequest.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer studentId = keyHolder.getKey().intValue();
        return studentId;
    }

    @Override
    public Student readById(Integer id) {
        String sql = "SELECT id, name from student where id= :studentId";
        Map<String, Object> map = new HashMap<>();
        RowMapper<Student> rowMapper = new StudentRowMapper();
        map.put("studentId", id);

        List<Student> list = namedParameterJdbcTemplate.query(sql, map, rowMapper);

        if(!list.isEmpty()){
            return list.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public void update(Integer Id, Request request) {
        String sql = "UPDATE student SET name = :studentName WHERE id = :studentId";

        StudentRequest studentRequest = (StudentRequest) request;
        Map<String, Object> map = new HashMap<>();

        map.put("studentId", Id);
        map.put("studentName", studentRequest.getName());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Integer bookId) {

    }

    @Override
    public void update(String Id, Request request) {

    }

    @Override
    public Student readById(String id) {
        return null;
    }
}
