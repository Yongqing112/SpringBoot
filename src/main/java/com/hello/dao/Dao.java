package com.hello.dao;

import com.hello.dto.Request;

public interface Dao <T>{
    T readById(String id);
    T readById(Integer id);

    Integer create(Request request);

    void update(Integer Id, Request request);

    void update(String Id, Request request);

    void delete(Integer Id);
}
