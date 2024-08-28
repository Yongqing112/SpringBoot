package com.hello.service.impl;

import com.hello.dao.Dao;
import com.hello.dto.StudentRequest;
import com.hello.entity.Student;
import com.hello.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentServiceImpl implements StudentService<Student> {

    @Autowired
    private Dao<Student> dao;

    @Override
    public Integer createStudent(StudentRequest studentRequest) {
        return dao.create(studentRequest);
    }

    public Student readById(Integer id) {
        return dao.readById(id);
    }

    @Override
    public void update(Integer id, StudentRequest studentRequest) {
        dao.update(id, studentRequest);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }
}
