package com.hello.mapper;

import com.hello.entity.Student;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException{
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("user_name"));
        return student;
    }

}
