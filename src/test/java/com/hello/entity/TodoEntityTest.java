package com.hello.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoEntityTest {

    @Test
    public void whenSetId_ThenGetId(){
        Todo todo = new Todo();

        todo.setId(1);

        Integer id = 1;

        assertEquals(id, todo.getId());
    }

    @Test
    public void whenSetTask_ThenGetTask(){
        Todo todo = new Todo();

        todo.setTask("曬衣服");

        String task = "曬衣服";

        assertEquals(task, todo.getTask());
    }

    @Test
    public void whenSetStatus_ThenGetStatus(){
        Todo todo = new Todo();

        todo.setStatus(2);

        Integer status = 2;

        assertEquals(status, todo.getStatus());
    }
}
