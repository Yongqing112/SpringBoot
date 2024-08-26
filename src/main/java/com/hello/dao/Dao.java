package com.hello.dao;

import com.hello.dto.Request;

public interface Dao <T>{
    T getById(String id);
    T getById(Integer id);

    Integer create(Request request);

    void update(Integer bookId, Request request);

    void delete(Integer bookId);
}
