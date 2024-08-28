package com.hello.service;

import com.hello.dto.StudentRequest;

public interface StudentService <T>{

    T readById(Integer id);

    Integer createStudent(StudentRequest studentRequest);

    void update(Integer id, StudentRequest studentRequest);

    void delete(Integer id);
}
