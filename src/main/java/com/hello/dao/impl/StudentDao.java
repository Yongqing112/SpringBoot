package com.hello.dao.impl;

import com.hello.dao.Dao;
import com.hello.dto.BookRequest;
import com.hello.entity.Student;
import com.hello.mapper.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDao implements Dao<Student> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Student getById(String id) {
        String sql = "SELECT id, user_name from user where id= :studentId";
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
    public Student getById(Integer id) {
        return null;
    }

    @Override
    public Integer create(BookRequest bookRequest) {
        return 0;
    }

    @Override
    public void update(Integer bookId, BookRequest bookRequest) {

    }

    @Override
    public void delete(Integer bookId) {

    }
}
