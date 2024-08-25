package com.hello.service.impl;

import com.hello.dao.Dao;
import com.hello.entity.Student;
import com.hello.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentServiceImpl implements StudentService<Student> {

    @Autowired
    private Dao<Student> dao;

    @Override
    public Student getById(String id) {
        return dao.getById(id);
    }
}
