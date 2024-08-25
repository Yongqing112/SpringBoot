package com.hello.service;

import com.hello.dto.BookRequest;

public interface BookService <T>{
    T getBookById(Integer bookId);

    Integer createBook(BookRequest bookRequest);

    void updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBookById(Integer bookId);
}
