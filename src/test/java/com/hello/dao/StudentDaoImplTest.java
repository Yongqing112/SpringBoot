package com.hello.dao;

import com.hello.dao.impl.StudentDao;
import com.hello.entity.other.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentDaoImplTest {

    //注入bean
    @Autowired
    private StudentDao studentDao;

    @Transactional
    @Test
    public void deleteById(){
        studentDao.delete(1);
        Student student =studentDao.readById(1);
        assertNull(student);
    }

    @Test
    public void getById(){
        Student student = studentDao.readById(1);
        assertNotNull(student);
        assertEquals("0828",student.getName());
    }
}