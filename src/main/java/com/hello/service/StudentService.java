package com.hello.service;

import com.hello.dto.StudentRequest;

public interface StudentService <T>{

    T getById(String id);

    Integer createStudent(StudentRequest studentRequest);
}
