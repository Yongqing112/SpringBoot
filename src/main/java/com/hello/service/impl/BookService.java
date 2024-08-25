package com.hello.service.impl;

import com.hello.dao.Dao;
import com.hello.dto.BookRequest;
import com.hello.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookService implements com.hello.service.BookService<Book> {
    @Autowired
    private Dao<Book> dao;

    @Override
    public Book getBookById(Integer bookId) {
        return dao.getById(bookId);
    }

    @Override
    public Integer createBook(BookRequest bookRequest) {
        return dao.create(bookRequest);
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest) {
        dao.update(bookId, bookRequest);
    }

    @Override
    public void deleteBookById(Integer bookId) {
        dao.delete(bookId);
    }
}
