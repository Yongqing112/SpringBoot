package com.hello.dao;

import com.hello.dto.BookRequest;

import java.util.List;

public interface Dao <T>{
    T getById(String id);
    T getById(Integer id);

    Integer create(BookRequest bookRequest);

    void update(Integer bookId, BookRequest bookRequest);

    void delete(Integer bookId);
}
